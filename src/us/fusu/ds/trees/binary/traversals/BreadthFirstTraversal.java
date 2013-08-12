package us.fusu.ds.trees.binary.traversals;

import java.util.LinkedList;
import java.util.Queue;

import us.fusu.ds.trees.binary.Node;

public class BreadthFirstTraversal extends BinaryTreeTraversal {

	@Override
	protected <V> void traverseRecursively(Node<V> argRoot, NodeOperation<V> argOperation) {
		// no recursion here
	}

	@Override
	protected <V> void traverseIteratively(Node<V> argRoot, NodeOperation<V> argOperation) {
		Queue<Node<V>> queue = new LinkedList<>();
		queue.offer(argRoot);
		
		while (!queue.isEmpty()) {
			argRoot = queue.poll();
			argOperation.visit(argRoot.data);
			if (argRoot.left != null) {
				queue.offer(argRoot.left);
			}
			if (argRoot.right != null) {
				queue.offer(argRoot.right);
			}
		}
	}
	
}
