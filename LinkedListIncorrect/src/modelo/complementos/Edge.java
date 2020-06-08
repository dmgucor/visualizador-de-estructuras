package modelo.complementos;

/**
 * Clase requerida para retornar los arcos
 * Se entiende como arco a la relaci�n entre un par de nodos para los grafos.
 * Se entiende como conexi�n a la relaci�n entre un par de nodos para los �rboles y listas encadenadas.
 */
public class Edge<K>{
	public K start;
	public K end;
	
	/**
	 * Constructor del arco/conexi�n
	 * @param inicio Objeto inicial de la relaci�n, es un nodo (implementaci�n del estudiante que contiene el m�todo toString para las etiquetas) o String
	 * @param fin Objeto final de la relaci�n, es un nodo (implementaci�n del estudiante que contiene el m�todo toString para las etiquetas) o String
	 */
	public Edge(K inicio, K fin) {
		start = inicio;
		end = fin;
	}
	public Edge() {
		
	}
}