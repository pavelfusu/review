package us.fusu.ds.map;

@SuppressWarnings({"unchecked","rawtypes"})
public class HashMap<K, V> implements Map<K, V> {

	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private static final int DEFAULT_INITIAL_CAPACITY = 128;
	private static final int MAXIMUM_CAPACITY = 1 << 30;

	private int size;
	private int threshold;
	private int capacity;
	private final float loadFactor;
	private Entry<K, V>[] buckets;

	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public HashMap(int argInitialCapacity) {
		this(argInitialCapacity, DEFAULT_LOAD_FACTOR);
	}


	public HashMap(int argInitialCapacity, float argLoadFactor) {
		capacity = argInitialCapacity;
		loadFactor = argLoadFactor;
		buckets = new Entry[capacity];
		threshold = (int)(capacity * loadFactor);
	}

	@Override
	public V get(K argKey) {
		if (argKey == null) {
			return null;
		}

		Entry<K,V> entry = getEntry(argKey);
		return entry == null ? null : entry.getValue();
	}

	@Override
	public V put(K argKey, V argValue) {

		if (argKey == null || argValue == null) {
			throw new NullPointerException();
		}

		int hash = hash(argKey);
		int i = indexFor(hash, buckets.length);

		for (Entry<K,V> e = buckets[i]; e != null; e = e.next) {
			K k;
			// if same hash and same reference or equal objects update value
			if (e.hash == hash && ((k = e.key) == argKey || argKey.equals(k))) {
				V oldValue = e.value;
				e.value = argValue;
				return oldValue;
			}
		}

		addEntry(hash, argKey, argValue, i);
		return null;
	}
	
	@Override
	public int size() {
		return size;
	}

	void addEntry(int argHash, K argKey, V argValue, int argIndex) {
		if (size >= threshold && buckets[argIndex] != null) {
			resize(2 * buckets.length);
			argHash = argKey != null ? hash(argKey) : 0;
			argIndex = indexFor(argHash, buckets.length);
		}

		Entry<K,V> e = buckets[argIndex];
		buckets[argIndex] = new Entry<>(argHash, argKey, argValue, e);
		size++;
	}

	void resize(int newCapacity) {
		int oldCapacity = buckets.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry[] newTable = new Entry[newCapacity];
		transfer(newTable);
		buckets = newTable;
		threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
		capacity = newCapacity;
	}

	void transfer(Entry[] newTable) {
		int newCapacity = newTable.length;
		for (Entry<K,V> e : buckets) {
			while (e != null) {
				Entry<K,V> next = e.next;
				e.hash = e.key == null ? 0 : hash(e.key);
				int i = indexFor(e.hash, newCapacity);
				e.next = newTable[i];
				newTable[i] = e;
				e = next;
			}
		}
	}

	Entry<K,V> getEntry(K key) {
		int hash = (key == null) ? 0 : hash(key);
		for (Entry<K, V> e = buckets[indexFor(hash, buckets.length)]; e != null; e = e.next) {
			K k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
				return e;
			}
		}
		return null;
	}

	static int indexFor(int h, int length) {
		return h & (length-1);
	}

	// Could apply additional hashing for keys with poor hashcode implementation
	private int hash(K argKey) {
		return argKey.hashCode();
	}

	static class Entry<K, V> implements Map.Entry<K, V> {

		K key;
		V value;
		int hash;
		Entry<K, V> next;

		public Entry(int argHash, K argKey, V argValue, Entry<K, V> argNext) {
			key = argKey;
			value = argValue;
			hash = argHash;
			next = argNext;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setKey(K argKey) {
			key = argKey;
		}

		public void setValue(V argValue) {
			value = argValue;
		}

	}

}
