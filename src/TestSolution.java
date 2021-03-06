import java.util.*;

import junit.framework.TestCase;

public class TestSolution
    extends TestCase {

  private Solution sol;

  public <T> List<Solution.TrieNode<T>> entryList(Solution.TrieNode<T> root) {
    List<Solution.TrieNode<T>> ret = new ArrayList<>();
    Stack<Solution.TrieNode<T>> stack = new Stack<>();
    Solution.TrieNode<T> node = root;

    while (!stack.isEmpty() || node != null) {
      if (node != null) {
        if (node.getValue() != null) {
          ret.add(node);
        }

        Iterator<Map.Entry<Character, Solution.TrieNode<T>>> it =
            node.treeMap.descendingMap().entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry<Character, Solution.TrieNode<T>> entry = it.next();
          stack.push(entry.getValue());
        }

        if (!stack.isEmpty()) {
          node = stack.pop();
        }
        else {
          node = null;
        }
      }
      else {
        node = stack.pop();
      }
    }

    return ret;
  }

  public void testRadixTreeDump() {
    Solution.TrieNode<Integer> root = new Solution.TrieNode<>();
    sol.radixTreeSet(root, "company", 1);
    sol.radixTreeSet(root, "computer", 2);
    sol.radixTreeSet(root, "comp", 0);
    sol.radixTreeSet(root, "coeducation", -2);
    sol.radixTreeSet(root, "coeducational", -1);
    sol.radixTreeSet(root, "coedu", -3);

    assertEquals("[-3, -2, -1, 0, 1, 2]", entryList(root).toString());
  }

  public void testRadixTreeGet()
      throws Exception {
    Solution.TrieNode<Integer> root = new Solution.TrieNode<>();
    sol.radixTreeSet(root, "company", 1);
    sol.radixTreeSet(root, "computer", 2);
    sol.radixTreeSet(root, "comp", 0);
    sol.radixTreeSet(root, "coeducation", -2);
    sol.radixTreeSet(root, "coeducational", -1);
    sol.radixTreeSet(root, "coedu", -3);

    assertEquals(new Integer(2), sol.radixTreeGet(root, "computer").getValue());
    assertEquals(new Integer(2), sol.radixTreeGet(root, "computer").getValue());
    assertEquals(new Integer(-1), sol.radixTreeGet(root, "coeducational").getValue());
    assertEquals(new Integer(-2), sol.radixTreeGet(root, "coeducation").getValue());
    assertEquals(new Integer(-2), sol.radixTreeGet(root, "coeducation").getValue());
    assertEquals(new Integer(-3), sol.radixTreeGet(root, "coedu").getValue());
    assertEquals(new Integer(0), sol.radixTreeGet(root, "comp").getValue());
    assertEquals(new Integer(1), sol.radixTreeGet(root, "company").getValue());
    assertNull(sol.radixTreeGet(root, "companion"));
    assertNull(sol.radixTreeGet(root, "coed"));
    assertNull(sol.radixTreeGet(root, "a"));
  }

  public void testRadixTreeRemove()
      throws Exception {
    Solution.TrieNode<Integer> root = new Solution.TrieNode<>();
    sol.radixTreeSet(root, "company", 1);
    sol.radixTreeSet(root, "computer", 2);
    sol.radixTreeSet(root, "comp", 0);
    sol.radixTreeSet(root, "coeducation", -2);
    sol.radixTreeSet(root, "coeducational", -1);
    sol.radixTreeSet(root, "coedu", -3);

    sol.radixTreeRemove(root, "company");
    assertEquals("[-3, -2, -1, 0, 2]", entryList(root).toString());

    sol.radixTreeRemove(root, "comp");
    assertEquals("[-3, -2, -1, 2]", entryList(root).toString());

    sol.radixTreeRemove(root, "a");
    assertEquals("[-3, -2, -1, 2]", entryList(root).toString());

    sol.radixTreeRemove(root, "computer");
    sol.radixTreeRemove(root, "coeducation");
    sol.radixTreeRemove(root, "coeducational");
    sol.radixTreeRemove(root, "coedu");

    assertEquals("[]", entryList(root).toString());

    sol.radixTreeSet(root, "company", 1);
    sol.radixTreeSet(root, "computer", 2);
    sol.radixTreeSet(root, "comp", 0);
    sol.radixTreeSet(root, "coeducation", -2);
    sol.radixTreeSet(root, "coeducational", -1);
    sol.radixTreeSet(root, "coedu", -3);

    sol.radixTreeRemove(root, "company");
    sol.radixTreeRemove(root, "comp");
    sol.radixTreeRemove(root, "computer");
    sol.radixTreeRemove(root, "coeducation");
    sol.radixTreeRemove(root, "coeducational");
    sol.radixTreeRemove(root, "coedu");

    assertEquals("[]", entryList(root).toString());

    sol.radixTreeSet(root, "s", 1);
    sol.radixTreeSet(root, "b", 2);
    sol.radixTreeSet(root, "d", 0);
    sol.radixTreeSet(root, "t", -2);
    sol.radixTreeSet(root, "z", -1);
    sol.radixTreeSet(root, "i", 12);
    sol.radixTreeSet(root, "zara", -3);
    sol.radixTreeSet(root, "zebra", -55);
    sol.radixTreeSet(root, "zinocerus", 21);
    sol.radixTreeSet(root, "zinocopolus", 89);
    sol.radixTreeSet(root, "sergeant", 14);
    sol.radixTreeSet(root, "sergey", 19);
    sol.radixTreeSet(root, "brotherhood", 290);
    sol.radixTreeSet(root, "brother", 190);

    sol.radixTreeRemove(root, "brother");
    assertEquals("[2, 290, 0, 12, 1, 14, 19, -2, -1, -3, -55, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "b");
    assertEquals("[290, 0, 12, 1, 14, 19, -2, -1, -3, -55, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "z");
    assertEquals("[290, 0, 12, 1, 14, 19, -2, -3, -55, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "zebra");
    assertEquals("[290, 0, 12, 1, 14, 19, -2, -3, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "brotherhood");
    assertEquals("[0, 12, 1, 14, 19, -2, -3, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "t");
    assertEquals("[0, 12, 1, 14, 19, -3, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "s");
    assertEquals("[0, 12, 14, 19, -3, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "sergey");
    assertEquals("[0, 12, 14, -3, 21, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "zinocerus");
    assertEquals("[0, 12, 14, -3, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "d");
    assertEquals("[12, 14, -3, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "sergeant");
    assertEquals("[12, -3, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "zara");
    assertEquals("[12, 89]", entryList(root).toString());
    sol.radixTreeRemove(root, "zinocopolus");
    assertEquals("[12]", entryList(root).toString());
    sol.radixTreeRemove(root, "i");
    assertEquals("[]", entryList(root).toString());
  }

  public void testRadixTreeSet()
      throws Exception {
    Solution.TrieNode<Integer> root = new Solution.TrieNode<>();
    sol.radixTreeSet(root, "company", 1);
    sol.radixTreeSet(root, "computer", 2);
    sol.radixTreeSet(root, "comp", 0);
    sol.radixTreeSet(root, "coeducation", -2);
    sol.radixTreeSet(root, "coeducational", -1);
    sol.radixTreeSet(root, "coedu", -3);
    assertEquals(true, isSorted(entryList(root)));
  }

  protected <T extends Comparable<T>> boolean isSorted(List<Solution.TrieNode<T>> argList) {
    Iterator<Solution.TrieNode<T>> it = argList.iterator();
    Solution.TrieNode<T> prev = null;
    while (it.hasNext()) {
      Solution.TrieNode<T> e = it.next();
      if (prev != null) {
        if (e.getValue().compareTo(prev.getValue()) < 0) {
          return false;
        }
      }

      prev = e;
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected void setUp()
      throws Exception {
    sol = new Solution();
  }

  /** {@inheritDoc} */
  @Override
  protected void tearDown()
      throws Exception {
    sol = null;
  }

}
