package us.fusu.ds.trees;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import us.fusu.ds.trees.binary.Node;
import us.fusu.ds.trees.binary.traversals.BinaryTreeTraversal;
import us.fusu.ds.trees.binary.traversals.TraversalType;
import us.fusu.io.IO;

public class BSTPuzzles {
	
	public static Node<Integer> lowestCommonAncestor(Node<Integer> root, Node<Integer> a, Node<Integer> b) {
		if (root == null || a == null || b == null) {
			return null;
		}

		if (Math.max(a.data, b.data) < root.data)
			return lowestCommonAncestor(root.left, a, b);
		else if (Math.min(a.data, b.data) > root.data)
			return lowestCommonAncestor(root.right, a, b);
		else
			return root;
	}

	public static void shortestpath(Node<Integer> root, Node<Integer> node, Stack<Node<Integer>> outputPath) {
		if (root == null) {
			return;
		}
		if (root.equals(node)) {
			outputPath.push(root);
			return;
		}

		if (node.data < root.data) {
			shortestpath(root.left, node, outputPath);
		} else {
			shortestpath(root.right, node, outputPath);
		}

		outputPath.push(root);
	}

	public static List<Node<Integer>> shortestPath(Node<Integer> root, Node<Integer> a, Node<Integer> b) {
		Stack<Node<Integer>> path1 = new Stack<>();
		Stack<Node<Integer>> path2 = new Stack<>();

		Node<Integer> lca = lowestCommonAncestor(root, a, b);

		Node<Integer> r = lca.equals(a) ? a : (lca.equals(b) ? b : lca);

		if (min(a, b).data < r.data) {
			shortestpath(r.left, min(a, b), path1);
		}

		if (max(a, b).data > r.data) {
			shortestpath(r.right, max(a, b), path2);
		}

		path1.push(r);
		while (!path2.isEmpty()) {
			path1.push(path2.pop());
		}
		return path1;
	}

	public static Node<Integer> min(Node<Integer> a, Node<Integer> b) {
		return a.data < b.data ? a : b;
	}

	public static Node<Integer> max(Node<Integer> a, Node<Integer> b) {
		return a.data > b.data ? a : b;
	}

	public static boolean isValidBST(Node<Integer> root) {
		if (root == null) {
			return false;
		}
		
		Node<Integer> lastVisited = null;

		Stack<Node<Integer>> stack = new Stack<>();
		while (!stack.isEmpty() || root != null) {
			if (root != null) {
				stack.push(root);
				root = root.left;
			} else {
				root = stack.pop();
				
				if (lastVisited != null && root.data <= lastVisited.data) {
					return false;
				}
				lastVisited = root;
				
				root = root.right;
			}
		}

		return true;
	}
	
	public static boolean isValidBSTRecursive(Node<Integer> root) {
		return isValidBST(root, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
	}
	public static boolean isValidBST(Node<Integer> curr, int min, int max) {
		if (curr.left != null) {
			if (curr.left.data < min ||
					!isValidBST(curr.left, min, curr.data))
				return false;
		}
		if (curr.right != null) {
			if (curr.right.data > max ||
					!isValidBST(curr.right, curr.data, max))
				return false;
		}
		return true;
	}
	
	public static Node<Integer> binaryPreorderToTree(int[] arr, int length, AtomicInteger index, int min, int max) {
		if (index.intValue() >= length) {
			return null;
		}

		Node<Integer> root = null;

		int currentNode = arr[index.intValue()];
		
		if (currentNode > min && currentNode < max) {
			root = new Node<>(currentNode);
			index.incrementAndGet();

			if (index.intValue() < length ) {
				root.left = binaryPreorderToTree(arr, length, index, min, currentNode);
			}

			if (index.intValue() < length) {
				root.right = binaryPreorderToTree(arr, length, index, currentNode, max);
			}
		}

		return root;
	}
	
	public static void main(String[] args) {
		Node<Integer> root = binaryPreorderToTree(new int[] {60, 41, 16, 25, 53, 46, 42, 55, 74, 65, 63, 62, 64, 70}, 14, new AtomicInteger(0), Integer.MIN_VALUE, Integer.MAX_VALUE);
		BinaryTreeTraversal.traverse(root, TraversalType.IN_ORDER, new BinaryTreeTraversal.NodeOperation<Integer>() {

			@Override
			public void visit(Integer argValue) {
				IO.write(argValue + " ");
			}
		});
	}
}
