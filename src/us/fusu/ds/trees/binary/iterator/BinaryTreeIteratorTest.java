package us.fusu.ds.trees.binary.iterator;

import java.util.Iterator;

import us.fusu.ds.trees.binary.Node;
import us.fusu.ds.trees.binary.traversals.TraversalType;

import static us.fusu.ds.trees.binary.traversals.TraversalType.*;

public class BinaryTreeIteratorTest {
	
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
		
		printTree(IN_ORDER, root);
		printTree(PRE_ORDER, root);
		printTree(POST_ORDER, root);
		printTree(BREADTH_FIRST, root);
	}
	
	public static <V> void printTree(TraversalType argType, Node<V> argRoot) {
		Iterator<V> it = BinaryTreeIterator.iterator(argType, argRoot);
		System.out.print("[ ");
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println("]");
	}
}
