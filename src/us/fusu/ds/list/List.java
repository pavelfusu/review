package us.fusu.ds.list;

public interface List<V> {
	
	public V removeFirst();
	
	public V removeLast();
	
	public void addFirst(V argValue);
	
	public void add(V argValue);
	
	public int size();

}
