package us.fusu.ds.trees.binary.traversals;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

public class PostOrderTraversal extends BinaryTreeTraversal {

	@Override
	protected <V> void traverseRecursively(Node<V> argRoot, NodeOperation<V> argOperation) {
		if (argRoot == null) {
			return;
		}
		
		traverseRecursively(argRoot.left, argOperation);
		traverseRecursively(argRoot.right, argOperation);
		argOperation.visit(argRoot.data);
	}

	@Override
	protected <V> void traverseIteratively(Node<V> argRoot, NodeOperation<V> argOperation) {
		if (argRoot == null) {
			return;
		}
		
		Node<V> current = argRoot;
		Stack<Node<V>> stack = new Stack<>();
		stack.push(current);
		Node<V> previous = null;
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
				argOperation.visit(current.data);
				stack.pop();
			}
			previous = current;
		}
	}

}
