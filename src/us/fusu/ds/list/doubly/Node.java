package us.fusu.ds.list.doubly;

public class Node<V> {
	public Node<V> prev;
	public Node<V> next;
	public V data;
	
	public Node(V argData) {
		data = argData;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}