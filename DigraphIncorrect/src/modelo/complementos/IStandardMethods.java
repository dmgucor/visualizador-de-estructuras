package modelo.complementos;

import java.util.ArrayList;

/**
 * Interfaz para la implementaci�n de los m�todos requeridos en el visualizador
 * Es necesario que haya un objeto est�tico de la estructura de datos, el cual se usa para llamar las funcionalidades implementadas en los m�todos
 * definidos en esta interface.
 * Se entiende como arco a la relaci�n entre un par de nodos para los grafos.
 * Se entiende como conexi�n a la relaci�n entre un par de nodos para los �rboles y listas encadenadas.
 * @author Diana Marcela Guti�rrez
 *Los nodos de la estructura deben implementar un m�todo toString() que retorne el valor que aparecer� en la etiqueta (�nica) que representa dicho nodo.
 * @param <K>
 */
public interface IStandardMethods<K> {

	void createStructure();

	/**
	 * Nodos en la estructura
	 * @return Lista con objetos tipos nodo (implementaci�n del estudiante que contiene el m�todo toString para las etiquetas) o Strings
	 */
	ArrayList<K> getNodeList();

	/**
	 * Arcos/conexiones en la estructura
	 * @return Lista con objetos tipo Edge (dada al estudiante)
	 * @return null si es lista encadenada
	 */
	ArrayList<Edge<K>> getEdgesList();

	/**
	 * B�squeda de nodo
	 * @param findTag etiqueta asociada al nodo que se va a buscar
	 * @return Objeto tipo nodo o String correspondiente al resultado de la b�squeda
	 */
	Object findNode(String findTag);

	/**
	 * B�squeda de arco/conexi�n
	 * @param startNode etiqueta asociada al nodo inicial
	 * @param endNode etiqueta asociada al nodo final
	 * @return Objeto tipo Edge correspondiente al resultado de la b�squeda
	 */
	Edge<K> findEdge(String startNode, String endNode);

	/**
	 * B�squeda de adyacentes
	 * @param idVertex etiqueta asociada al nodo de b�squeda
	 * @return Lista de objetos tipo Edge con los resultados de la b�squeda
	 */
	ArrayList<Edge<K>> getNeighbors(String idVertex);
	
	/**
	 * Conjunto de arcos
	 * @return Lista de objetos tipo Edge con los arcos/conexiones de inter�s
	 */
	ArrayList<Edge<K>> showEdgesSet(); 

	/**
	 * Conjunto de nodos
	 * @return Lista con objetos tipo nodo (implementaci�n del estudiante que contiene el m�todo toString para las etiquetas) o Strings
	 */
	ArrayList<K> showNodeSet();

	/**
	 * Tipo de representaci�n para la visualizaci�n de la estructura
	 * @return true para listas encadenadas y �rboles
	 * @return false para grafos
	 */
	Boolean isLineal();

	/**
	 * Adici�n de un nuevo nodo
	 * @param tag etiqueta asociada al nodo que se va a agregar
	 */
	void addNode(String tag);

	/**
	 * Eliminaci�n de un nodo 
	 * @param tag etiqueta asociada al nodo a eliminar
	 */
	void deleteNode(String tag);

	ArrayList<K> getPath();

	/**
	 * Tipo de estructura del estudiante
	 * @return 0 para �rbol
	 * @return 1 para lista encadenada 
	 * @return 2 para grafo dirigido
	 * @return 3 para grafo no dirigido
	 */
	int structureType();

	/**
	 * Adici�n de un arco a la estructura
	 * No implementar si es un �rbol o lista encadenada
	 * @param startNode etiqueta asociada al nodo inicial
	 * @param endNode etiqueta asociada al nodo final
	 */
	void addEdge(String startNode, String endNode);
}

