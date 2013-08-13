package us.fusu.ds.list.singly;

public class Node<V> {
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
