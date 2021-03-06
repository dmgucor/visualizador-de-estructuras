package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.BinaryTree;

public class StandardMethods implements IStandardMethods<Object> {

	static BinaryTree bt;

	@Override
	public void createStructure() {	
		bt = new BinaryTree(100);
		bt.fillTree();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getNodeList() {
		return bt.getVertices();
	}

	@Override
	public ArrayList<Edge<Object>> getEdgesList() {
		bt.parseEdges();
		return bt.getEdges();
	}

	@Override
	public Object findNode(String findTag) {
		return bt.getNode(findTag);
	}

	@Override
	public Edge<Object> findEdge(String startNode, String endNode) {
		return bt.getEdge(startNode, endNode);
	}

	@Override
	public ArrayList<Edge<Object>> getNeighbors(String idVertex) {
		return bt.getNeighbors(idVertex);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList showNodeSet() {
		return bt.showNodeSet();
	}

	@Override
	public Boolean isLineal() {
		return true;
	}

	@Override
	public Boolean addNode(String tag) {
		return bt.insertarNodo(tag);		
	}

	@Override
	public Boolean deleteNode(String tag) {
		return bt.deleteNode(tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> getPath() {
		return bt.getPath();
	}

	@Override
	public int structureType() {
		return 0;
	}

	@Override
	public boolean addEdge(String startNode, String endNode) {
		return false;
	}

	@Override
	public ArrayList<Edge<Object>> showEdgesSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
