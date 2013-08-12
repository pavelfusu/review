package us.fusu.ds.trees;

import java.util.List;
import java.util.Stack;

import us.fusu.ds.trees.binary.Node;

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
}
