package us.fusu.ds.map;

public interface Map<K, V> {
	
	public V get(K argKey);
	
	public V put(K argKey, V argValue);
	
	static interface Entry<K, V> {
		public K getKey();
		
		public V getValue();
		
		public void setKey(K argKey);
		
		public void setValue(V argValue);
	}
}
