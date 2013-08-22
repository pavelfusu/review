// $Id$
package us.fusu.ds.trees;

import java.util.Iterator;

import us.fusu.ds.trees.nary.Node;

public class NTreePuzzles {

  public static <V> Node<V> lowestCommonAncestor(Node<V> argRoot, Node<V> a, Node<V> b) {
    if (argRoot == null) {
      return null;
    }

    if (argRoot.equals(a) || argRoot.equals(b)) {
      // if at least one matched, no need to continue
      // this is the LCA for this root
      return argRoot;
    }

    Iterator<Node<V>> it = argRoot.childIterator();
    int i = 0; // nr of branches that a or b are on, could be max 2 (considering unique nodes)
    Node<V> lastFoundLCA = null;
    while (it.hasNext()) {
      Node<V> node = lowestCommonAncestor(it.next(), a, b);
      if (node != null) {
        lastFoundLCA = node;
        i++ ;
      }
      if (i >= 2) {
        return argRoot;
      }
    }

    return lastFoundLCA;
  }

  public static void main(String[] args) {
    Node<Integer> root = new Node<>(3, 3);

    Node<Integer> five = new Node<>(5, 2);
    five.add(new Node<>(10));
    five.add(new Node<>(11));

    Node<Integer> seven = new Node<>(7, 2);
    seven.add(new Node<>(3));
    seven.add(new Node<>(4));

    Node<Integer> nine = new Node<>(9, 3);

    Node<Integer> six = new Node<>(6, 3);
    six.add(new Node<>(20));
    six.add(new Node<>(21));
    Node<Integer> n22 = new Node<>(22);
    six.add(n22);

    Node<Integer> eight = new Node<>(8, 3);
    eight.add(new Node<>(23));
    eight.add(new Node<>(24));
    eight.add(new Node<>(25));

    Node<Integer> ten = new Node<>(10);

    nine.add(six);
    nine.add(eight);
    nine.add(ten);

    root.add(five);
    root.add(seven);
    root.add(nine);

    Node<Integer> lca = lowestCommonAncestor(root, n22, ten);
    System.out.println(lca.data);
  }
}
