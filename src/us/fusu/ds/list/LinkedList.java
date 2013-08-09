package us.fusu.ds.list;

public class LinkedList<V> implements List<V> {
	
	private Node<V> first;
	private Node<V> last;
	private int size;
	
	public LinkedList() {}
	
	@Override
	public V removeFirst() {
		Node<V> f = first;
        if (f == null) {
        	return null;
        }
        V element = f.value;
        Node<V> next = f.next;
        f.value = null;
        f.next = null;
        first = next;
        if (next == null) {
        	last = null;
        } 
        size--;
        return element;
	}


	@Override
	public V removeLast() {
		return null;
	}
	
	
	@Override
	public void addFirst(V argValue) {
		Node<V> f = first;
        Node<V> newNode = new Node<>(argValue, f);
        first = newNode;
        if (f == null) {
        	last = newNode;
        }
        size++;
	}


	@Override
	public void add(V argValue) {
		final Node<V> l = last;
        final Node<V> newNode = new Node<>(argValue, null);
        last = newNode;
        if (l == null) {
        	first = newNode;
        } else {
        	l.next = newNode;
        }
        size++;
	}
	
	static class Node<V> {
		V value;
		Node<V> next;
		
		public Node(V argValue, Node<V> argNext) {
			value = argValue;
			next = argNext;
		}
	}

	@Override
	public int size() {
		return size;
	}

}
