package us.fusu.ds.trees.binary.iterator;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

/**
 * An iterator that iterates through a tree using in-order tree traversal<br/>
 * allowing a sorted sequence.
 */
public class InOrderIterator<V> extends BinaryTreeIterator<V>{

	private Stack<Node<V>> stack = new Stack<>();
	private Node<V> current;

	InOrderIterator(Node<V> argRoot) {
		current = argRoot;
	}

	@Override
	public V next() {
		while (current != null) {
			stack.push(current);
			current = current.left;
		}
				
		current = stack.pop();
		Node<V> node = current;
		current = current.right;

		return node.data;
	}

	@Override
	public boolean hasNext() {
		return (!stack.isEmpty() || current != null);
	}
	
}
