package us.fusu.ds.trees.binary.iterator;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

public class PostOrderIterator<V> extends BinaryTreeIterator<V> {

	private Stack<Node<V>> stack = new Stack<>();
	private Node<V> current;
	private Node<V> previous;

	PostOrderIterator(Node<V> argRoot) {
		current = argRoot;
		stack.push(current);
	}

	@Override
	public boolean hasNext() {
		return (!stack.isEmpty());
	}

	@Override
	public V next() {
		Node<V> ret = null;
		while (!stack.isEmpty()) {
			current = stack.peek();
			if (previous == null || previous.left == current || previous.right == current) {
				if (current.left != null) {
					stack.push(current.left);
				} else if (current.right != null) {
					stack.push(current.right);
				}
			} else if (current.left == previous) {
				if (current.right != null) {
					stack.push(current.right);
				}
			} else {
				ret = current;
				stack.pop();
				break;
			}
			
			previous = current;
		}
		
		previous = current;
		return ret != null ? ret.data : null;
	}

}
