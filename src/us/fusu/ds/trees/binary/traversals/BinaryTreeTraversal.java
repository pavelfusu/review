package us.fusu.ds.trees.binary.traversals;

import java.util.HashMap;
import java.util.Map;

import us.fusu.ds.trees.binary.Node;

import static us.fusu.ds.trees.binary.traversals.TraversalType.BREADTH_FIRST;

public abstract class BinaryTreeTraversal {
	
	protected <V> void traverse(Node<V> argRoot, NodeOperation<V> argOperation) {
		if (isRecursive()) {
			traverseRecursively(argRoot, argOperation);
		} else {
			traverseIteratively(argRoot, argOperation);
		}
	}
	
	protected abstract <V> void traverseRecursively(Node<V> argRoot, NodeOperation<V> argOperation);
	
	protected abstract <V> void traverseIteratively(Node<V> argRoot, NodeOperation<V> argOperation);
	
	
	
	private static Map<TraversalType, BinaryTreeTraversal> traversals = new HashMap<>();
	private static boolean isRecursive = false;
	
	public static BinaryTreeTraversal forType(TraversalType argType) {
		if (traversals.containsKey(argType)) {
			return traversals.get(argType);
		}
		
		switch (argType) {
			case IN_ORDER: {
				traversals.put(argType, new InOrderTraversal());
				break;
			}
			case PRE_ORDER: {
				traversals.put(argType, new PreOrderTraversal());
				break;
			}
			case POST_ORDER: {
				traversals.put(argType, new PostOrderTraversal());
				break;
			}
			case BREADTH_FIRST: {
				traversals.put(argType, new BreadthFirstTraversal());
				break;
			}
		}
		
		return traversals.containsKey(argType) ? traversals.get(argType) : traversals.get(BREADTH_FIRST);
	}
	
	public static <V> void traverse(Node<V> argRoot, TraversalType argType, NodeOperation<V> argOperation) {
		forType(argType).traverse(argRoot, argOperation);
	}
	
	public static interface NodeOperation<V> {
		void visit(V argValue);
	}

	protected static boolean isRecursive() {
		return isRecursive;
	}
	
	public static void setRecursive(boolean argIsRecursive) {
		isRecursive = argIsRecursive;
	}
}
