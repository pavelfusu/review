package us.fusu.ds.list.singly;

public class Entry<V> {
	public Entry<V> next;
	public V data;
	
	public Entry(V argData) {
		data = argData;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
