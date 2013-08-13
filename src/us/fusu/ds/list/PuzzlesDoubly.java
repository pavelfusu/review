package us.fusu.ds.list;

import us.fusu.ds.list.doubly.Node;
import us.fusu.io.IO;

public class PuzzlesDoubly {
	
	public static <V> Node<V> reverse(Node<V> argHead) {
		Node<V> previous = null;
		
		while (argHead != null) {
			Node<V> next = argHead.next;
			argHead.next = previous;
			previous = argHead;
			argHead.prev = next;
			argHead = next;
		}
		
		return previous;
	}
	
	public static <V> Node<V> reverseRecursive(Node<V> argHead) {
		if (argHead == null) {
			return argHead;
		}
		
		Node<V> next = argHead.next;
		Node<V> prev = argHead.prev;
		argHead.prev = next;
		argHead.next = prev;

		if (next.next == null) {
			next.next = argHead;
			next.prev = null;
			return next;
		} else {
			return reverseRecursive(next);
		}
	}
	
	public static <V> Node<V> asList(V[] arr) {
		Node<V> head = new Node<>(arr[0]);
		Node<V> current = head;
		Node<V> previous = null;
		for (int i = 1; i < arr.length; i++) {
			current.next = new Node<>(arr[i]);
			current.prev = previous;
			previous = current;
			current = current.next; 
		}
		
		return head;
	}
	
	public static <V> void print(Node<V> node) {
		IO.write("[ ");
		while (node != null) {
			IO.write(node.data + " ");
			node = node.next;
		}
		IO.writeLn("]");
	}

	public static void main(String[] args) {
		Node<Integer> head = asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		print(head);
		head = reverseRecursive(head);
		print(head);
	}
}
