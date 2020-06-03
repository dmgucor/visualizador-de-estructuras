package modelo.estructuras;

import java.util.ArrayList;
import java.util.Iterator;

import modelo.complementos.Edge;

public class UndirectedGraph<K, V> implements IGraph<K, V> {
	// -----------------------------------------------------------------
	// Atributos.
	// -----------------------------------------------------------------

	private int nVertices;
	private int nArcos;
	private LinearProbingHash<K,Vertice> HT;
	private KosarajuSCC cfcs;

	LinearProbingHash<K, Boolean> marked;
	ArrayList<K> vertices = new ArrayList<K>();
	private Stack<K> reversePost;


	// -----------------------------------------------------------------
	// Metodos.
	// -----------------------------------------------------------------

	public UndirectedGraph() {
		this.nVertices = 0;
		this.nArcos = 0;
		HT = new LinearProbingHash<K,Vertice>();
	}

	public UndirectedGraph(int V) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.nVertices = V;
		this.nArcos = 0;
		HT = new LinearProbingHash<K,Vertice>(V);
	}

	public int V() {
		return nVertices;
	}

	public int E() {
		return nArcos;
	}

	@Override
	public void addVertex(K idVertex, V infoVertex) {
		Vertice nuevoNodo = new Vertice( infoVertex, idVertex );
		HT.put(idVertex, nuevoNodo);
		nVertices++;
	}

	@Override
	public void addEdge(K idVertexIni, K idVertexFin, Double infoArc) {

		//Se obtienen los vertices de inicio y fin del arco y se crea el arco
		Vertice inicio = HT.get(idVertexIni);
		DiArco nuevo = new DiArco(infoArc,idVertexIni,idVertexFin);

		// se agrega el arco al nodo de inicio.
		LinkedList<DiArco> lista = inicio.adyacentes;
		lista.add(nuevo);
		inicio.adyacentes = lista;

		nArcos++;
	}

	@Override
	public V getInfoVertex(K idVertex) {
		return HT.get(idVertex) != null ? HT.get(idVertex).item : null;
	}

	@Override
	public void setInfoVertex(K idVertex, V infoVertex) {

		if(HT.get(idVertex) != null) {
			HT.get(idVertex).item = infoVertex;
		}

	}

	@Override
	public Double getInfoArc(K idVertexIni, K idVertexFin) {
		LinkedList<DiArco> lista = HT.get(idVertexIni).adyacentes;

		Double retorno = null;

		for(DiArco a: lista) {

			if( a.fin.equals(idVertexFin) ) {
				retorno = a.info;
			}
		}
		return retorno;
	}

	@Override
	public void setInfoArc(K idVertexIni, K idVertexFin, Double infoArc) {

		LinkedList<DiArco> lista = HT.get(idVertexIni).adyacentes;

		for(DiArco a: lista) {
			if( a.fin.equals( idVertexFin ) ) 
				a.info = infoArc;
		}
	}

	public UndirectedGraph<K, V> reverse() {
		UndirectedGraph<K, V> R = new UndirectedGraph<K, V>();
		Iterable<K> keys = HT.keys();
		for( K key : keys ) {
			R.addVertex( key, HT.get(key).item );
		}
		for( K v : keys ) {
			Iterable<DiArco> adj = HT.get(v).adyacentes;
			for( DiArco arc : adj ) {
				R.addEdge( arc.fin , arc.inicio, arc.info );
			}
		}
		return R;
	}

	@Override
	public Iterable<K> adj(K idVertex) {
		Queue<K> respuesta = new Queue<K>();
		LinkedList<DiArco> arrayArcos = HT.get(idVertex).adyacentes;
		for( DiArco arco : arrayArcos ) {
			respuesta.enqueue( arco.fin );
		}
		return respuesta;
	}

	@Override
	public Iterator<K> iterator() {
		return HT.keys().iterator();
	}

	public Iterable<DiArco> edges(){
		LinkedList<DiArco> listaArcos = new LinkedList<DiArco>();

		for( K key : HT.keys() ) {
			Vertice v = HT.get(key);
			for( DiArco e : v.adyacentes ) {
				listaArcos.add( e );
			}
		}

		return listaArcos;
	}

	public boolean stronglyConnected( K v, K w ) {  
		if( cfcs == null ) cfcs = new KosarajuSCC();
		return cfcs.id.get(v).equals( cfcs.id.get(w) );
	}
	public int id(K v){
		if( cfcs == null ) cfcs = new KosarajuSCC();
		return cfcs.id.get(v);
	}
	public int count(){
		if( cfcs == null ) cfcs = new KosarajuSCC();
		return cfcs.count;
	}


	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------

	private class KosarajuSCC{
		LinearProbingHash<K, Boolean> marked;
		LinearProbingHash<K, Integer> id;
		int count;

		public KosarajuSCC() {
			marked = new LinearProbingHash<K, Boolean>();
			id = new LinearProbingHash<K, Integer>();

			for( K k : HT.keys() ) 
				marked.put( k , false);

			DepthFirstOrder order = new DepthFirstOrder( reverse() );
			for (K s : order.reversePost())
				if (!marked.get(s)) {
					dfs(s);
					count++;
				}
		}

		private void dfs( K v ) {
			marked.put(v, true);
			id.put(v, count);

			for (K w : adj(v))
				if ( !marked.get(w) )
					dfs( w );
		}
	}

	private class DepthFirstOrder{
		LinearProbingHash<K, Boolean> marked;
		ArrayList<K> vertices = new ArrayList<K>();
		private Stack<K> reversePost;
		public DepthFirstOrder( UndirectedGraph<K,V> G ) {
			reversePost   = new Stack<K>();
			marked  = new LinearProbingHash<K, Boolean>();

			for ( K v : HT.keys() ) 
				marked.put( v , false);


			for ( K v : HT.keys() )
				if (!marked.get(v)) dfs(G, v);

			for(K vert : vertices) System.out.println(vert);
		}

		private void dfs(UndirectedGraph<K,V> G, K v)
		{
			marked.put(v, true);

			for (K w : adj(v))
				if (!marked.get(w)) {
					vertices.add(v);
					dfs(G, w);
				}

			reversePost.push(v);
		}

		public Iterable<K> reversePost()
		{  return reversePost;  }

	}

	public ArrayList<Vertice> getNodes(){	
		ArrayList<Vertice> vertices = new ArrayList<UndirectedGraph<K,V>.Vertice>();
		for( K key : HT.keys() ) {
			Vertice v = HT.get(key);
			vertices.add(v);
		}

		return vertices;
	}

	public ArrayList<Edge<K>> getEdges() {
		ArrayList<Edge<K>> edges = new ArrayList<Edge<K>>();
		for(DiArco arco : edges()) {
			if(HT.get(arco.fin) != null) {
				Edge<K> edge = new Edge<K>(arco.inicio, arco.fin);
				edges.add(edge);
			}
		}
		return edges;
	}

	public Vertice getNode(K idVertex) {
		return HT.get(idVertex);
	}

	public Edge<K> findEdge(String start, String end){
		Edge<K> edge = new Edge<K>();
		for(Edge<K> current : getEdges()) {
			if(current.start.toString().equals(start) && current.end.toString().equals(end)) {
				edge = current;
				break;
			}
		}
		return edge;
	}

	public ArrayList<Edge<K>> getNeighbors(K idVertex) {
		ArrayList<Edge<K>> edges = new ArrayList<Edge<K>>();
		Vertice inicio = HT.get(idVertex);
		LinkedList<DiArco> lista = inicio.adyacentes;
		for(DiArco arco : lista) {
			if(HT.get(arco.fin) != null) {
				Edge<K> edge = new Edge<K>(arco.inicio, arco.fin);
				edges.add(edge);
			}
		}
		return edges;
	}

	public ArrayList<Vertice> startingNodeSet(){
		ArrayList<Vertice> nodeSet = new ArrayList<Vertice>();
		for( K key : HT.keys() ) {
			if(Math.random() >= 0.87) {
				Vertice v = HT.get(key);
				nodeSet.add(v);
			}
		}

		return nodeSet;
	}

	public void deleteNode(K tag) {
		HT.delete(tag);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getPath() {
		ArrayList<K> n = dfo(this);
		ArrayList ret = new ArrayList();
		for(int i = 0; i < n.size() - 1; i++) {
			if(findEdge(n.get(i).toString(), n.get(i+1).toString()).start != null) {
				if(!ret.contains(n.get(i))) ret.add(n.get(i));
				if(!ret.contains(n.get(i+1))) ret.add(n.get(i+1));
			}
			else {
				break;
			}
			
		}
		return ret;
	}
	
	// -----------------------------------------------------------------
	// Recorrido
	// -----------------------------------------------------------------


	public ArrayList<K> dfo(UndirectedGraph<K, V> graph) {
		reversePost   = new Stack<K>();
		marked  = new LinearProbingHash<K, Boolean>();

		for ( K v : HT.keys() ) 
			marked.put( v , false);


		for ( K v : HT.keys() )
			if (!marked.get(v)) dfs(graph, v);

		return vertices;
	}

	private void dfs(UndirectedGraph<K,V> G, K v)
	{
		marked.put(v, true);

		for (K w : adj(v)) {
			if(!vertices.contains(v)) vertices.add(v);
			if (!marked.get(w)) {
				dfs(G, w);
			}
		}
		reversePost.push(v);
	}

	public Iterable<K> reversePost()
	{  return reversePost;  }

	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------

	/**
	 * Clase privada que representa los vertices (nodos) del grafo.
	 */
	public class Vertice{
		private V item;
		private LinkedList<DiArco> adyacentes;
		private K tag;

		public Vertice( V pItem, K tag ) {
			item = pItem;
			this.tag = tag;
			adyacentes = new LinkedList<DiArco>();
		}

		public String toString() {
			return  Integer.toString((int) tag);
		}

		public String getInfo() {
			return (String) item;
		}
	}

	/**
	 * Clase privada que representa los arcos del grafo.
	 */
	public class DiArco implements Comparable<DiArco>{
		private K inicio;
		private K fin;
		private Double info;

		public DiArco( Double pInfo, K pInicio, K pFin ) {
			info = pInfo;
			inicio = pInicio;
			fin = pFin;
		}

		@Override
		public int compareTo(DiArco o) {
			return info.compareTo( o.info );
		}

		public double getInfo() {
			return info;
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		int graphSize = 10;
		UndirectedGraph graph = new UndirectedGraph(graphSize);
		for (int i = 0; i < graphSize; i++) {
			graph.addVertex(i, "nodo numero " + i);
			System.out.println("nuevo vertice : " + i);
		}

		for(int i = 0; i < graphSize; i++) {
			int numEdges = (int) Math.floor(Math.random() * graphSize);
			for (int j = 0; j < numEdges; j++) {
				int destination = (int) Math.floor(Math.random() * graphSize);
				graph.addEdge(i, destination, 0.5);
				System.out.println("arco: " + i +" -> " + destination);
			}
		}

		ArrayList n = graph.getPath();
		for (int i = 0; i < n.size(); i++) {
			System.out.println(n.get(i).toString() + "hhh");
		}		
	}

}