package us.fusu.ds.list;

import us.fusu.ds.list.doubly.Entry;
import us.fusu.io.IO;

public class PuzzlesDoubly {
	
	public static <V> Entry<V> reverse(Entry<V> argHead) {
		Entry<V> previous = null;
		
		while (argHead != null) {
			Entry<V> next = argHead.next;
			argHead.next = previous;
			previous = argHead;
			argHead.prev = next;
			argHead = next;
		}
		
		return previous;
	}
	
	public static <V> Entry<V> reverseRecursive(Entry<V> argHead) {
		if (argHead == null) {
			return argHead;
		}
		
		Entry<V> next = argHead.next;
		Entry<V> prev = argHead.prev;
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
	
	public static <V> Entry<V> asList(V[] arr) {
		Entry<V> head = new Entry<>(arr[0]);
		Entry<V> current = head;
		Entry<V> previous = null;
		for (int i = 1; i < arr.length; i++) {
			current.next = new Entry<>(arr[i]);
			current.prev = previous;
			previous = current;
			current = current.next; 
		}
		
		return head;
	}
	
	public static <V> void print(Entry<V> node) {
		IO.write("[ ");
		while (node != null) {
			IO.write(node.data + " ");
			node = node.next;
		}
		IO.writeLn("]");
	}

	public static void main(String[] args) {
		Entry<Integer> head = asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		print(head);
		head = reverseRecursive(head);
		print(head);
	}
}
