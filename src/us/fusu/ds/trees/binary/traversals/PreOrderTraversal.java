package us.fusu.ds.trees.binary.traversals;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

public class PreOrderTraversal extends BinaryTreeTraversal {

	@Override
	protected <V> void traverseRecursively(Node<V> argRoot, NodeOperation<V> argOperation) {
		if (argRoot == null) {
			return;
		}

		argOperation.visit(argRoot.data);
		traverseRecursively(argRoot.left, argOperation);
		traverseRecursively(argRoot.right, argOperation);
	}

	@Override
	protected <V> void traverseIteratively(Node<V> argRoot, NodeOperation<V> argOperation) {
		Stack<Node<V>> stack = new Stack<>();
		
		while (!stack.isEmpty() || argRoot != null) {
			if (argRoot != null) {
				argOperation.visit(argRoot.data);
				if (argRoot.right != null) {
					stack.push(argRoot.right);
				}
				argRoot = argRoot.left;
			} else {
				argRoot = stack.pop();
			}
		}
	}

}
