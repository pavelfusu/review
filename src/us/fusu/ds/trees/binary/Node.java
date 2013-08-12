package us.fusu.ds.trees.binary;

public class Node<V> {
	public Node<V> left;
	public Node<V> right;
	
	public V data;
	
	public Node(V argData) {
		data = argData;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
	
}
