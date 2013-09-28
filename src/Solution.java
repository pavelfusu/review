import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

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
      String radix = longestCommonPrefix(child.getRadix(), key);

      if (radix.length() == child.getRadix().length()) {
        if (radix.length() == key.length()) { // got it
          return child;
        }
        else { // child is actually a prefix of the key we are looking for
          return radixTreeGet(child, key.substring(radix.length()));
        }
      }
    }

    return null;
  }

  protected static <T> void radixTreeMove(TrieNode<T> node, TrieNode<T> parent) {
    if (parent.getChild(node.getChar()) == null) {
      parent.addChild(node.getChar(), node);
    }
    else {
      Iterator<Map.Entry<Character, TrieNode<T>>> it = node.treeMap.entrySet().iterator();
      TrieNode<T> childParent = radixTreeSet(parent, node.getRadix(), node.getValue());
      while (it.hasNext()) {
        Map.Entry<Character, TrieNode<T>> e = it.next();
        TrieNode<T> current = e.getValue();

        Iterator<Map.Entry<Character, TrieNode<T>>> it1 = current.treeMap.entrySet().iterator();
        current = radixTreeSet(childParent, current.getRadix(), current.getValue());

        while (it1.hasNext()) {
          TrieNode<T> child = it1.next().getValue();
          radixTreeMove(child, current);
        }
      }
    }
  }

  protected static <T> void radixTreeRemove(TrieNode<T> node, String key) {
    Character ch = key.charAt(0);
    TrieNode<T> child = node.getChild(ch);

    if (child != null) {
      String radix = longestCommonPrefix(child.getRadix(), key);

      if (radix.length() == child.getRadix().length()) {
        if (radix.length() == key.length()) { // got it
          node.removeChild(ch);

          if (child.childrenNumber() == 0) {
            if (node.childrenNumber() == 1 && node.getValue() == null && node.getRadix() != null) {
              TrieNode<T> onlyChild = node.getFirstChild();
              node.setValue(onlyChild.getValue());
              node.arr = onlyChild.arr;
              node.treeMap = onlyChild.treeMap;
              node.radix += onlyChild.radix;
              node.ch = node.radix.charAt(0);
              onlyChild.setValue(null);
              onlyChild.treeMap = null;
              onlyChild.ch = null;
              onlyChild.radix = null;
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
            Iterator<Map.Entry<Character, Solution.TrieNode<T>>> it = child.treeMap.entrySet().iterator();

            while (it.hasNext()) {
              Solution.TrieNode<T> toMove = it.next().getValue();
              toMove.ch = ch;
              toMove.radix = ch + toMove.radix;
              radixTreeMove(toMove, node);
            }

            child.treeMap = null;
          }

          child.setValue(null);
        }
        else { // child is actually a prefix of the key we are adding
          radixTreeRemove(child, key.substring(radix.length()));
        }
      }
    }
  }

  protected static <T> TrieNode<T> radixTreeSet(TrieNode<T> node, String key, T value) {
    Character ch = key.charAt(0);
    TrieNode<T> child = node.getChild(ch);

    if (child == null) {
      child = new TrieNode<T>(ch, key);
      child.setValue(value);
      node.addChild(ch, child);
    }
    else {

      String radix = longestCommonPrefix(child.getRadix(), key);

      if (radix.length() == child.getRadix().length()) {
        if (radix.length() == key.length()) { // updating existing key
          child.setValue(value);
          return child;
        }
        else { // child is actually a prefix of the key we are adding
          return radixTreeSet(child, key.substring(radix.length()), value);
        }
      }
      else if (radix.length() < child.getRadix().length()) {
        if (radix.length() == key.length()) { // key we're looking for is a prefix of child
          TrieNode<T> split = new TrieNode<>(ch, radix);
          split.setValue(value);
          node.removeChild(ch);
          node.addChild(ch, split);
          child.radix = child.radix.substring(radix.length());
          child.ch = child.radix.charAt(0);
          split.addChild(child.ch, child);
        }
        else if (radix.length() < key.length()) {
          TrieNode<T> split = new TrieNode<>(ch, radix);
          node.removeChild(ch);
          node.addChild(ch, split);
          child.radix = child.radix.substring(radix.length());
          child.ch = child.radix.charAt(0);
          split.addChild(child.ch, child);
          return radixTreeSet(split, key.substring(radix.length()), value);
        }
      }

    }

    return child;
  }

  protected static <T> void radixTreeTraversal(TrieNode<T> root, RadixTreeVisitor<T> visitor) {
    TrieNode<T> node = root;

    traversalStack.clear();

    while (!traversalStack.isEmpty() || node != null) {
      if (node != null) {
        if (node.getValue() != null) {
          visitor.visit(node);
        }

        Iterator<Map.Entry<Character, Solution.TrieNode<T>>> it =
            node.treeMap.descendingMap().entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry<Character, Solution.TrieNode<T>> entry = it.next();
          traversalStack.push(entry.getValue());
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
    protected TrieNode<V>[] arr = new TrieNode[96];
    protected TreeMap<Character, TrieNode<V>> treeMap = new TreeMap<>();

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
      arr[argCh - 32] = argNode;
      treeMap.put(argCh, argNode);
    }

    public int childrenNumber() {
      return treeMap.size();
    }

    public Character getChar() {
      return ch;
    }

    public TrieNode<V> getChild(Character argCh) {
      return arr[argCh - 32];
    }

    public TrieNode<V> getFirstChild() {
      Iterator<Map.Entry<Character, TrieNode<V>>> it = treeMap.entrySet().iterator();
      return it.hasNext() ? it.next().getValue() : null;
    }

    public String getRadix() {
      return radix;
    }

    public V getValue() {
      return value;
    }

    public void removeChild(Character argCh) {
      arr[argCh - 32] = null;
      treeMap.remove(argCh);
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

    private TrieNode<Entry<K, V>> root = new TrieNode<>();

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
            head = new Entry<>(k, v);
            tail = head;
          }
          else {
            head.prev = new Entry<>(k, v);
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
    }

    private Entry<K, V> trieGet(String word) {
      TrieNode<Entry<K, V>> node = radixTreeGet(root, word);
      return node != null ? node.getValue() : null;
    }

    private void trieRemove(String word) {
      radixTreeRemove(root, word);
    }

    private Entry<K, V> trieSet(String word, Entry<K, V> entry) {
      TrieNode<Entry<K, V>> node = radixTreeSet(root, word, entry);
      return node != null ? node.getValue() : null;
    }
  }
}