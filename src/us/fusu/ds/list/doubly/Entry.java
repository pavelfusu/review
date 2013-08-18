package us.fusu.ds.list.doubly;

public class Entry<V> extends us.fusu.ds.list.singly.Entry<V> {
	public Entry<V> prev;
	public Entry<V> next;
	
	public Entry(V argData) {
		super(argData);
	}
	
}