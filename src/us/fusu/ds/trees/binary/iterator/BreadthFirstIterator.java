package us.fusu.ds.trees.binary.iterator;

import java.util.LinkedList;
import java.util.Queue;

import us.fusu.ds.trees.binary.Node;

public class BreadthFirstIterator<V> extends BinaryTreeIterator<V> {
	
	private Queue<Node<V>> queue = new LinkedList<>();
	private Node<V> current;
	
	BreadthFirstIterator(Node<V> argRoot) {
		current = argRoot;
		queue.offer(current);
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public V next() {
		current = queue.poll();
		
		if (current.left != null) {
			queue.offer(current.left);
		}
		if (current.right != null) {
			queue.offer(current.right);
		}
		
		return current.data;
	}

}
