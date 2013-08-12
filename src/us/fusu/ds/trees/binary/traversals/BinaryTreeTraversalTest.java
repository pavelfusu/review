package us.fusu.ds.trees.binary.traversals;

import static us.fusu.ds.trees.binary.traversals.TraversalType.BREADTH_FIRST;
import static us.fusu.ds.trees.binary.traversals.TraversalType.IN_ORDER;
import static us.fusu.ds.trees.binary.traversals.TraversalType.POST_ORDER;
import static us.fusu.ds.trees.binary.traversals.TraversalType.PRE_ORDER;

import us.fusu.ds.trees.binary.Node;
import us.fusu.ds.trees.binary.traversals.BinaryTreeTraversal.NodeOperation;

public class BinaryTreeTraversalTest {

	public static void main(String[] args) {
		Node<Integer> root = new Node<>(60);
		root.left = new Node<>(41);
		root.right = new Node<>(74);
		root.left.left = new Node<>(16);
		root.left.right = new Node<>(53);
		root.left.left.right = new Node<>(25);
		root.left.right.left = new Node<>(46);
		root.left.right.right = new Node<>(55);
		root.left.right.left.left = new Node<>(42);
		root.right.left = new Node<>(65);
		root.right.left.left = new Node<>(63);
		root.right.left.right = new Node<>(70);
		root.right.left.left.left = new Node<>(62);
		root.right.left.left.right = new Node<>(64);
		
		NodeOperation<Integer> operation = new NodeOperation<Integer>() {

			@Override
			public void visit(Integer argValue) {
				System.out.print(argValue + " ");
			}
			
		};
		
		BinaryTreeTraversal.setRecursive(true);
		
		printTree(IN_ORDER, root, operation);
		printTree(PRE_ORDER, root, operation);
		printTree(POST_ORDER, root, operation);
		printTree(BREADTH_FIRST, root, operation);
	}
	
	public static <V> void printTree(TraversalType argType, Node<V> argRoot, NodeOperation<V> argOperation) {
		System.out.print("[ ");
		BinaryTreeTraversal.traverse(argRoot, argType, argOperation);
		System.out.println("]");
	}
}
