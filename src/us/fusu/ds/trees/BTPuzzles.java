package us.fusu.ds.trees;

import java.util.List;
import java.util.Stack;

import us.fusu.ds.trees.binary.Node;
import us.fusu.io.IO;

public class BTPuzzles {
	
	public static <V> Node<V> lowestCommonAncestor(Node<V> root, Node<V> a, Node<V> b) {
		if (root == null) return null;
		if (root.equals(a) || root.equals(b)) {
			return root;
		}

		Node<V> l = lowestCommonAncestor(root.left, a, b);
		Node<V> r = lowestCommonAncestor(root.right, a, b);

		if (l != null && r != null) {
			return root;  // if p and q are on both sides
		}

		return l != null ? l : r;  // either one of p,q is on one side OR p,q is not in L&R subtrees
	}
	
	public static <V> void shortestpath(Node<V> root, Node<V> a, Node<V> b, Stack<Node<V>> outputPath) {
		if (root == null) {
			return;
		}
		if (root.equals(a) || root.equals(b)) {
			outputPath.push(root);
			return;
		}
		
		shortestpath(root.left, a, b, outputPath);
		
		if (outputPath.isEmpty()) {
			shortestpath(root.right, a, b, outputPath);
		} else {
			outputPath.push(root);
		}
	}

	public static <V> List<Node<V>> shortestPath(Node<V> root, Node<V> a, Node<V> b) {
		Stack<Node<V>> path1 = new Stack<>();
		Stack<Node<V>> path2 = new Stack<>();
		
		Node<V> lca = lowestCommonAncestor(root, a, b);
		
		Node<V> r = lca.equals(a) ? a : (lca.equals(b) ? b : lca);
		
		shortestpath(r.left, a, b, path1);
		shortestpath(r.right, a, b, path2);
		path1.push(r);
		while (!path2.isEmpty()) {
			path1.push(path2.pop());
		}
		return path1;
	}
	
	/**
	 * Prints boundary nodes of a binary tree
	 * @param root - the root node
	 */
	public static <V> void printOutsidesOfBinaryTree(Node<V> root) {

		Stack<Node<V>> rightSide = new Stack<>();
		Stack<Node<V>> stack = new Stack<>();
		stack.push(root);

		boolean printingLeafs = false;
		Node<V> node = stack.pop();

		while (node != null) {

			// add all the non-leaf right nodes left
			// to a separate stack
			if (stack.isEmpty() && printingLeafs && 
					(node.left != null || node.right != null)) {
				rightSide.push(node);
			}

			if (node.left == null && node.right == null) {
				// leaf node, print it out
				printingLeafs = true;
				IO.write(node.data + " ");
				node = stack.isEmpty() ? null : stack.pop();
			} else {
				if (!printingLeafs) {
					IO.write(node.data + " ");
				}

				if (node.left != null && node.right != null) {
					stack.push(node.right);
				}
				node = node.left != null ? node.left : node.right;
			}
		}

		// print out any non-leaf right nodes (if any left)
		while (!rightSide.isEmpty()) {
			IO.write(rightSide.pop().data + " ");
		}
	}
	
	public static <V> boolean equals(Node<V> root1, Node<V> root2) {
		if (root1 == null || root2 == null) {
			if (root1 == null && root2 == null) {
				return true;
			} else {
				return false;
			}
		}
		
		return (root1 == root2 || root1.equals(root2)) && equals(root1.left, root2.left) && equals(root1.right, root2.right);
	}
	
	public static <V> boolean equalsIterative(Node<V> root1, Node<V> root2) {
		Stack<Node<V>> stack1 = new Stack<>();
		Stack<Node<V>> stack2 = new Stack<>();
		
		while (!stack1.isEmpty() || !stack2.isEmpty() || root1 != null || root2 != null) {
			
			if ((root1 == null && root2 != null) || (root1 != null && root2 == null)) {
				return false;
			}
			
			if (root1 != root2 && !root1.equals(root2)) {
				return false;
			}
			
			if (stack1.size() != stack2.size()) {
				return false;
			}
			
			if (root1 != null && root2 != null) {
				stack1.push(root1);
				stack2.push(root2);
				root1 = root1.left;
				root2 = root2.left;
			} else {
				root1 = stack1.pop().right;
				root2 = stack2.pop().right;
			}
		}
		
		return true;
	}
	
}