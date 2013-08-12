package us.fusu.ds.trees.binary.iterator;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

/**
 * An iterator that iterates through a tree using pre-order tree traversal<br/>
 */
public class PreOrderIterator<V> extends BinaryTreeIterator<V> {
	
	private Stack<Node<V>> stack = new Stack<>();
	private Node<V> current;

	PreOrderIterator(Node<V> argRoot) {
		current = argRoot;
	}

	@Override
	public boolean hasNext() {
		return (!stack.isEmpty() || current != null);
	}

	@Override
	public V next() {
		Node<V> node = current;
		
		if (current.right != null) {
			stack.push(current.right);
		}
		
		if (current.left != null) {
			stack.push(current.left);
		}
		
		if (!stack.isEmpty()) {
			current = stack.pop();
		} else {
			current = null;
		}

		return node.data;
	}

}
