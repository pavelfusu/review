package us.fusu.ds.trees.binary.traversals;

import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

public class InOrderTraversal extends BinaryTreeTraversal {
	
	@Override
	protected <V> void traverseRecursively(Node<V> argRoot, NodeOperation<V> argOperation) {
		if (argRoot == null) {
			return;
		}
		
		traverseRecursively(argRoot.left, argOperation);
		argOperation.visit(argRoot.data);
		traverseRecursively(argRoot.right, argOperation);
	}
	
	protected <V> void traverseIteratively(Node<V> argRoot, NodeOperation<V> argOperation) {
		Stack<Node<V>> stack = new Stack<>();
		while (!stack.isEmpty() || argRoot != null) {
			if (argRoot != null) {
				stack.push(argRoot);
				argRoot = argRoot.left;
			} else {
				argRoot = stack.pop();
				argOperation.visit(argRoot.data);
				argRoot = argRoot.right;
			}
		}
	}
	
}
