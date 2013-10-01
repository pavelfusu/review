import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

@SuppressWarnings({"javadoc", "unchecked"})
public class Solution {

  private static final Stack<TrieNode<?>> traversalStack = new Stack<>();

  private static final RadixTreeVisitor<Entry<String, String>> visitor =
      new RadixTreeVisitor<Entry<String, String>>() {

        @Override
        public void visit(TrieNode<Entry<String, String>> argNode) {
          System.out.println(argNode.getValue());
        }

      };

  public static void main(String[] args)
      throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Solution solution = new Solution();
    solution.process(br);
  }

  protected static String longestCommonPrefix(String s1, String s2) {
    int i;
    for (i = 0; i < s1.length() && i < s2.length() && s1.charAt(i) == s2.charAt(i); i++ ) {}
    return s1.substring(0, i);
  }

  protected static <T> TrieNode<T> radixTreeGet(TrieNode<T> node, String key) {
    Character ch = key.charAt(0);
    TrieNode<T> child = node.getChild(ch);

    if (child != null) {
      if (key.startsWith(child.getRadix())) {
        if (child.getRadix().length() == key.length()) { // got it
          return child;
        }
        else { // child is actually a prefix of the key we are looking for
          return radixTreeGet(child, key.substring(child.getRadix().length()));
        }
      }
    }

    return null;
  }

  protected static <T> void radixTreeMove(TrieNode<T> node, TrieNode<T> parent, TrieNodeFactory<T> factory) {
    if (parent.getChild(node.getChar()) == null) {
      parent.addChild(node.getChar(), node);
    }
    else {
      TrieNode<T> childParent = radixTreeSet(parent, node.getRadix(), node.getValue(), factory);

      for (int i = 0; i < node.children.length; i++ ) {
        if (node.children[i] != null) {
          TrieNode<T> current =
              radixTreeSet(childParent, node.children[i].getRadix(), node.children[i].getValue(), factory);

          for (int j = 0; j > current.children.length; j++ ) {
            if (current.children[i] != null) {
              radixTreeMove(current.children[i], current, factory);
            }
          }
        }
      }
    }
  }

  protected static <T> void radixTreeRemove(TrieNode<T> node, String key, TrieNodePool<T> factory) {
    Character ch = key.charAt(0);
    TrieNode<T> child = node.getChild(ch);

    if (child != null) {
      if (key.startsWith(child.getRadix())) {
        if (key.length() == child.getRadix().length()) { // got it
          node.removeChild(ch);

          if (child.childrenNumber() == 0) {
            if (node.childrenNumber() == 1 && node.getValue() == null && node.getRadix() != null) {
              TrieNode<T> onlyChild = node.getFirstChild();
              node.setValue(onlyChild.getValue());
              System.arraycopy(onlyChild.children, 0, node.children, 0, onlyChild.children.length);
              node.radix += onlyChild.radix;
              node.ch = node.radix.charAt(0);
            }
          }
          else if (child.childrenNumber() == 1) {
            TrieNode<T> childOfChild = child.getFirstChild();
            child.removeChild(childOfChild.ch);
            childOfChild.radix = child.radix + childOfChild.radix;
            childOfChild.ch = childOfChild.radix.charAt(0);
            node.addChild(childOfChild.ch, childOfChild);
          }
          else {
            for (int i = 0; i < child.children.length; i++ ) {
              if (child.children[i] != null) {
                Solution.TrieNode<T> toMove = child.children[i];
                toMove.ch = ch;
                toMove.radix = ch + toMove.radix;
                radixTreeMove(toMove, node, factory);
              }
            }
          }

          factory.markAvailable(child);
        }
        else { // child is actually a prefix of the key we are adding
          String radix = longestCommonPrefix(child.getRadix(), key);
          radixTreeRemove(child, key.substring(radix.length()), factory);
        }
      }
    }
  }

  protected static <T> TrieNode<T> radixTreeSet(TrieNode<T> node, String key, T value,
      TrieNodeFactory<T> factory) {
    Character ch = key.charAt(0);
    TrieNode<T> child = node.getChild(ch);

    if (child == null) {
      child = factory.getTrieNode();
      child.ch = ch;
      child.radix = key;
      child.setValue(value);
      node.addChild(ch, child);
    }
    else {
      if (key.startsWith(child.getRadix())) {
        if (child.getRadix().length() == key.length()) { // updating existing key
          child.setValue(value);
          return child;
        }
        else { // child is actually a prefix of the key we are adding
          return radixTreeSet(child, key.substring(child.getRadix().length()), value, factory);
        }
      }
      else {
        String radix = longestCommonPrefix(child.getRadix(), key);
        if (radix.length() < key.length()) {
          TrieNode<T> split = factory.getTrieNode();
          split.ch = ch;
          split.radix = radix;
          node.removeChild(ch);
          node.addChild(ch, split);
          child.radix = child.radix.substring(radix.length());
          child.ch = child.radix.charAt(0);
          split.addChild(child.ch, child);
          return radixTreeSet(split, key.substring(radix.length()), value, factory);
        }
      }

    }

    return child;
  }

  protected static <T> void radixTreeTraversal(TrieNode<T> root, RadixTreeVisitor<T> argVisitor) {
    TrieNode<T> node = root;

    traversalStack.clear();

    while (!traversalStack.isEmpty() || node != null) {
      if (node != null) {
        if (node.getValue() != null) {
          argVisitor.visit(node);
        }

        for (int i = 0; i < node.children.length; i++ ) {
          if (node.children[i] != null) {
            traversalStack.push(node.children[i]);
          }
        }

        if (!traversalStack.isEmpty()) {
          node = (TrieNode<T>) traversalStack.pop();
        }
        else {
          node = null;
        }
      }
      else {
        node = (TrieNode<T>) traversalStack.pop();
      }
    }
  }

  public void process(BufferedReader br)
      throws Exception {

    final LRUCache<String, String> cache = new LRUCache<>();

    String line = null;
    String[] tokens;
    int count = Integer.parseInt(br.readLine());
    for (int i = 0; i < count; ++i) {
      line = br.readLine();
      tokens = line.split("\\s+");
      switch (tokens[0]) {
        case "DUMP": {
          dump(cache);
          break;
        }
        case "SET": {
          set(cache, tokens[1], tokens[2]);
          break;
        }
        case "GET": {
          get(cache, tokens[1]);
          break;
        }
        case "PEEK": {
          peek(cache, tokens[1]);
          break;
        }
        case "BOUND": {
          bound(cache, Integer.parseInt(tokens[1]));
          break;
        }
      }
    }
  }

  private void bound(LRUCache<String, String> cache, int bound) {
    cache.bound(bound);
  }

  private void dump(LRUCache<String, String> cache) {
    radixTreeTraversal(cache.root, visitor);
  }

  private void get(LRUCache<String, String> cache, String key) {
    String value = cache.get(key);
    System.out.println(value != null ? value : "NULL");
  }

  private void peek(LRUCache<String, String> cache, String key) {
    String value = cache.peek(key);
    System.out.println(value != null ? value : "NULL");
  }

  private void set(LRUCache<String, String> cache, String key, String value) {
    cache.set(key, value);
  }

  public static class TrieNode<V> {
    protected Character ch;
    protected String radix;
    protected TrieNode<V>[] children = new TrieNode[96];
    protected int count;

    private V value;

    public TrieNode() {
      this(null, null);
    }

    public TrieNode(Character argCh) {
      this(argCh, argCh + "");
    }

    public TrieNode(Character argCh, String argRadix) {
      ch = argCh;
      radix = argRadix;
    }

    public TrieNode(String argRadix) {
      this(argRadix.charAt(0), argRadix);
    }

    public void addChild(Character argCh, TrieNode<V> argNode) {
      children[argCh - 32] = argNode;
      count++ ;
    }

    public int childrenNumber() {
      return count;
    }

    public void clear() {
      radix = null;
      ch = null;
      children = new TrieNode[96];
      value = null;
    }

    public Character getChar() {
      return ch;
    }

    public TrieNode<V> getChild(Character argCh) {
      return children[argCh - 32];
    }

    public TrieNode<V> getFirstChild() {
      for (int i = 0; i < 96; i++ ) {
        if (children[i] != null) {
          return children[i];
        }
      }
      return null;
    }

    public String getRadix() {
      return radix;
    }

    public V getValue() {
      return value;
    }

    public void removeChild(Character argCh) {
      children[argCh - 32] = null;
      count-- ;
    }

    public void setValue(V argValue) {
      value = argValue;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
      return value != null ? value.toString() : "";
    }

  }

  public static interface TrieNodeFactory<V> {
    public TrieNode<V> getTrieNode();

    public void markAvailable(TrieNode<V> node);
  }

  protected static interface RadixTreeVisitor<T> {
    public void visit(TrieNode<T> node);
  }

  private static class Entry<K, V> {
    V value;
    K key;
    Entry<K, V> next;
    Entry<K, V> prev;

    Entry(K k, V v) {
      key = k;
      value = v;
    }

    @Override
    public final String toString() {
      return key + " " + value;
    }
  }

  private static class LRUCache<K, V> {
    private Entry<K, V> head;
    private Entry<K, V> tail;

    private int bound;
    private int size;

    private LRUEntryPool<K, V> lruEntryPool = new LRUEntryPool<>();
    private TrieNodePool<Entry<K, V>> trieNodePool = new TrieNodePool<>();
    private TrieNode<Entry<K, V>> root = trieNodePool.getTrieNode();

    public LRUCache() {
      bound = 100;
    }

    public void bound(int argBound) {
      if (argBound < 0) {
        return;
      }

      while (size > argBound) {
        removeFromTail();
        size-- ;
      }

      bound = argBound;
    }

    public V get(K k) {
      Entry<K, V> entry = trieGet(k.toString());
      if (entry != null) {
        moveToHead(entry);
        return entry.value;
      }
      else {
        return null;
      }
    }

    public V peek(K k) {
      Entry<K, V> entry = trieGet(k.toString());
      if (entry != null) {
        return entry.value;
      }
      else {
        return null;
      }
    }

    public void set(K k, V v) {
      if (bound > 0) {
        Entry<K, V> entry = trieGet(k.toString());

        if (entry != null) {
          entry.value = v;
          moveToHead(entry);
        }
        else {
          if (size >= bound) {
            removeFromTail();
          }
          else {
            size++ ;
          }

          if (head == null) {
            head = lruEntryPool.getEntry(k, v);
            tail = head;
          }
          else {
            head.prev = lruEntryPool.getEntry(k, v);
            head.prev.next = head;
            head = head.prev;
          }

          trieSet(head.key.toString(), head);
        }
      }
    }

    private void moveToHead(Entry<K, V> entry) {
      if (entry == null) {
        return;
      }

      if (head == null) {
        head = entry;
        tail = entry;
        entry.prev = null;
        entry.next = null;
      }
      else if (entry != head) {
        entry.prev.next = entry.next;
        if (entry != tail) {
          entry.next.prev = entry.prev;
        }
        else {
          tail = entry.prev;
        }
        head.prev = entry;
        entry.next = head;
        entry.prev = null;
        head = entry;
      }
    }

    private void removeFromTail() {
      if (tail == null) {
        return;
      }

      Entry<K, V> oldTail = tail;

      if (tail.prev == null) {
        tail = null;
        head = null;
      }
      else {
        tail = oldTail.prev;
        tail.next = null;
        oldTail.prev = null;
      }

      trieRemove(oldTail.key.toString());
      lruEntryPool.markAvailable(oldTail);
    }

    private Entry<K, V> trieGet(String word) {
      TrieNode<Entry<K, V>> node = radixTreeGet(root, word);
      return node != null ? node.getValue() : null;
    }

    private void trieRemove(String word) {
      radixTreeRemove(root, word, trieNodePool);
    }

    private Entry<K, V> trieSet(String word, Entry<K, V> entry) {
      TrieNode<Entry<K, V>> node = radixTreeSet(root, word, entry, trieNodePool);
      return node != null ? node.getValue() : null;
    }
  }

  private static class LRUEntryPool<K, V> {
    private Entry<K, V>[] availableEntries = new Entry[350];
    private int count = 0;

    public Entry<K, V> getEntry(K key, V value) {
      if (count == 0) {
        return new Entry<K, V>(key, value);
      }
      else {
        Entry<K, V> entry = availableEntries[count - 1];
        entry.key = key;
        entry.value = value;
        availableEntries[ --count] = null;
        return entry;
      }
    }

    public void markAvailable(Entry<K, V> entry) {
      entry.key = null;
      entry.value = null;
      availableEntries[count++ ] = entry;
    }
  }

  private static class TrieNodePool<V>
      implements TrieNodeFactory<V> {

    private TrieNode<V>[] availableNodes = new TrieNode[350];
    private int count = 0;

    @Override
    public TrieNode<V> getTrieNode() {
      if (count == 0) {
        return new TrieNode<>();
      }
      else {
        TrieNode<V> node = availableNodes[count - 1];
        availableNodes[ --count] = null;
        return node;
      }
    }

    @Override
    public void markAvailable(TrieNode<V> node) {
      node.clear();
      availableNodes[count++ ] = node;
    }
  }
}