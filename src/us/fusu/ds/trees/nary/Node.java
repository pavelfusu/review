// $Id$
package us.fusu.ds.trees.nary;

import java.util.*;

public class Node<V> {

  private Node<V>[] children;
  private int childrenSize;
  public V data;

  public Node(V argData) {
    data = argData;
    children = null;
  }

  public Node(V argData, int nrChildren) {
    this(argData);
    children = new Node[nrChildren];
  }

  public void add(Node<V> argChild) {
    if (childrenSize >= children.length) {
      throw new IllegalArgumentException();
    }

    children[childrenSize++ ] = argChild;
  }

  public Iterator<Node<V>> childIterator() {
    if (children == null) {
      return new ArrayList<Node<V>>().iterator();
    }
    return Arrays.asList(children).iterator();
  }
}
