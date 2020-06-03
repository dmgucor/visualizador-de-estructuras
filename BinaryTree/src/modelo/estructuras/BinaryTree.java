package modelo.estructuras;

import java.util.ArrayList;

import modelo.complementos.Edge;

public class BinaryTree
{
	private ArrayList<Edge<Object>> arcos;
	private ArrayList<NodoArbol> vertices;
	private ArrayList<NodoArbol> camino = new ArrayList<NodoArbol>();
	int size;

	NodoArbol aux = null;

	class NodoArbol
	{
		//miembros de acceso
		NodoArbol nodoizquierdo;
		int datos;
		NodoArbol nododerecho;
		ArrayList<NodoArbol> subarbol;

		//iniciar dato y hacer de este nodo un nodo hoja
		public NodoArbol(int datosNodo)
		{
			datos = datosNodo;
			nodoizquierdo = nododerecho = null; //el nodo no tiene hijos
		}

		//buscar punto de insercion e inserter nodo nuevo
		public NodoArbol insertar(int valorInsertar){

			//insertar en subarbol izquierdo
			if(valorInsertar < datos) {
				//insertar en subarbol izquierdo
				if(nodoizquierdo == null) {
					nodoizquierdo = new NodoArbol(valorInsertar);
					aux = nodoizquierdo;
				}
				else    //continua recorriendo subarbol izquierdo
					nodoizquierdo.insertar(valorInsertar);
			}

			//insertar nodo derecho
			else if(valorInsertar > datos) {
				//insertar nuevo nodoArbol
				if(nododerecho == null) {
					nododerecho = new NodoArbol(valorInsertar);
					aux = nododerecho;
				}
				else {
					nododerecho.insertar(valorInsertar);
				}
			}

			return aux;
		} 

		public String toString() {
			return Integer.toString(datos);
		}

		public NodoArbol deleteNode(int tag) {
			if(datos == tag) {
				if(nodoizquierdo == null && nododerecho == null) {
					return null;
				}
				else if(nodoizquierdo == null) {
					return nododerecho;
				}
				else if(nododerecho == null) {
					return nodoizquierdo;
				}
				else {
					NodoArbol reemplazo = getMinor(nododerecho); 
					nododerecho = nododerecho.deleteNode(reemplazo.datos);
					reemplazo.nodoizquierdo = nodoizquierdo;
					reemplazo.nododerecho = nododerecho;
					return reemplazo;
				}
			}
			else if(datos > tag) {
				nodoizquierdo = nodoizquierdo.deleteNode(tag);
				return this;
			}
			else {
				nododerecho = nododerecho.deleteNode(tag);
				return this;
			}

		}

		private NodoArbol getMinor(NodoArbol nodo) {
			subarbol = new ArrayList<BinaryTree.NodoArbol>();
			recorrerSubarbol(nodo);
			NodoArbol menor = nodo;
			for(NodoArbol current : subarbol) {
				if(current.datos < menor.datos) menor = current;
			}
			return menor;
		}

		private void recorrerSubarbol(NodoArbol nodo)
		{
			if( nodo == null )
				return;
			subarbol.add(nodo);
			recorrerSubarbol(nodo.nodoizquierdo);
			recorrerSubarbol(nodo.nododerecho);
			//System.out.print(nodo.datos + " ");
		}

	}


	private NodoArbol raiz;

	//construir un arbol vacio
	public BinaryTree(int size)
	{
		raiz = null;
		arcos = new ArrayList<Edge<Object>>();
		vertices  = new ArrayList<NodoArbol>();
		this.size = size;
	}

	//insertar un nuevo ndo en el arbol de busqueda binaria
	public void insertarNodo(int valorInsertar)
	{
		if(raiz == null) {
			raiz = new NodoArbol(valorInsertar);
			vertices.add(raiz);
		}//crea nodo raiz
		else if(!inStructure(valorInsertar)){
			vertices.add(raiz.insertar(valorInsertar)); //llama al metodo insertar   
		}

	}

	// EMPIEZA EL RECORRIDO EN PREORDEN
	public synchronized void recorridoPreorden()
	{
		ayudantePreorden(raiz);
	}
	//meoto recursivo para recorrido en preorden

	private void ayudantePreorden(NodoArbol nodo)
	{
		if(nodo == null)
			return;

		camino.add(nodo);
		ayudantePreorden(nodo.nodoizquierdo);   //recorre subarbol izquierdo

	}


	//EMPEZAR RECORRIDO INORDEN
	public synchronized void recorridoInorden()
	{
		ayudanteInorden(raiz);
	}

	//meoto recursivo para recorrido inorden
	private void ayudanteInorden( NodoArbol nodo)
	{
		if(nodo == null)
			return;

		ayudanteInorden(nodo.nodoizquierdo);
		//System.out.print(nodo.datos + " ");
		ayudanteInorden(nodo.nododerecho);
	}

	//EMPEZAR RECORRIDO PORORDEN
	public synchronized void recorridoPosorden()
	{
		ayudantePosorden(raiz);        
	}

	//meotod recursivo para recorrido posorden
	private void ayudantePosorden(NodoArbol nodo)
	{
		if( nodo == null )
			return;

		ayudantePosorden(nodo.nodoizquierdo);
		ayudantePosorden(nodo.nododerecho);
		System.out.print(nodo.datos + " ");
	}

	public void fillTree() {
		if(size > 0) {
			int rootNode = (int) Math.floor(Math.random()*size);
			insertarNodo(rootNode);
			for (int i = 0; i < size; i++) {
				int nodoAct = (int) Math.floor(Math.random()*size);
				if(nodoAct != rootNode) 
					insertarNodo(nodoAct);
			}
		}
	}

	public void parseEdges() {
		arcos = new ArrayList<Edge<Object>>();
		for (int i = 0; i < vertices.size(); i++) {
			NodoArbol izquierdo = vertices.get(i).nodoizquierdo ;
			NodoArbol derecho = vertices.get(i).nododerecho;
			if(izquierdo != null) {
				Edge<Object> nuevo = new Edge<Object>(vertices.get(i), izquierdo);
				//System.out.println(vertices.get(i).datos + " -> " +  izquierdo.datos);
				arcos.add(nuevo);
			}
			if(derecho != null) {
				Edge<Object> nuevo = new Edge<Object>(vertices.get(i), derecho);
				//System.out.println(vertices.get(i).datos + " -> " +  derecho.datos);
				arcos.add(nuevo);
			}
		}
	}

	public NodoArbol getNode(String id) {
		NodoArbol ret = null;
		for(NodoArbol a : vertices) {
			//ret = (a.datos == Integer.parseInt(id)) ? a : null ;
			if(a.datos == Integer.parseInt(id)) {
				ret = a;
				break;
			}
		}
		return ret;
	}

	public boolean inStructure(int tag) {
		for (int i = 0; i < vertices.size(); i++) {
			if(vertices.get(i).datos == tag)
				return true;
		}
		return false;
	}

	public Edge<Object> getEdge(String start, String end){
		Edge<Object> ret = null;
		for(Edge<Object> edge : arcos) {
			if(edge.start.toString().equals(start) && edge.end.toString().equals(end)) {
				ret = edge;
				break;
			}
		}
		return ret;
	}

	public ArrayList<NodoArbol> getVertices(){
		return vertices;
	}

	public ArrayList<Edge<Object>> getEdges(){
		parseEdges();
		return arcos;
	}

	public void deleteNode(String tag) {
		raiz.deleteNode(Integer.parseInt(tag));
		vertices.remove(getNode(tag));
		parseEdges();
	}

	public NodoArbol getParent(NodoArbol son) {
		NodoArbol result = null;
		ArrayList<NodoArbol> verticesList = getVertices();
		for(NodoArbol current : verticesList) 
			if(current.nododerecho == son || current.nodoizquierdo == son) {
				result = current;
				break;
			}
		return result;
	}

	public ArrayList<Edge<Object>> getNeighbors(int tag){
		ArrayList<Edge<Object>> neighbors = new ArrayList<Edge<Object>>();
		for(Edge<Object> edge : arcos) {
			if(edge.start.toString().equals(Integer.toString(tag))) {
				neighbors.add(edge);
			}
		}
		return neighbors;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getPath(){
		camino = new ArrayList<BinaryTree.NodoArbol>();
		recorridoPreorden();
		return camino;
	}

	public ArrayList<NodoArbol> showNodeSet(){
		ArrayList<NodoArbol> nodeSet = new ArrayList<BinaryTree.NodoArbol>();
		ArrayList<NodoArbol> nodesInStructure = getVertices();
		for (int i = 0; i < nodesInStructure.size(); i++) {
			if(i % 5 == 0) nodeSet.add(nodesInStructure.get(i));				
		}
		return nodeSet;
	}


	public static void main(String[] args) {
		BinaryTree bst = new BinaryTree(0);
		bst.fillTree();
		bst.parseEdges();
		System.out.println("\n");
		ArrayList<NodoArbol> vert = bst.vertices;
		for(NodoArbol n : vert) System.out.println(n.datos);
	}


}
