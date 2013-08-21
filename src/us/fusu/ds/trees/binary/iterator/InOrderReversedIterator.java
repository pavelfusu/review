package us.fusu.ds.trees.binary.iterator;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

/**
 * An iterator that iterates through a tree using reversed in-order tree traversal<br/>
 * allowing a descending sorted sequence.
 */
public class InOrderReversedIterator<V>
    extends BinaryTreeIterator<V> {

  private Stack<Node<V>> stack = new Stack<>();
  private Node<V> current;

  InOrderReversedIterator(Node<V> argRoot) {
    current = argRoot;
  }

  @Override
  public boolean hasNext() {
    return (!stack.isEmpty() || current != null);
  }

  @Override
  public V next() {
    while (current != null) {
      stack.push(current);
      current = current.right;
    }

    current = stack.pop();
    Node<V> node = current;
    current = current.left;

    return node.data;
  }

}
