package us.fusu.ds.list;

import us.fusu.ds.list.doubly.Node;
import us.fusu.io.IO;

public class Puzzles {
	
	public static <V> Node<V> reverseDoublyLinkedList(Node<V> argHead) {
		
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
	
	public static <V> Node<V> toList(V[] arr) {
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
		Node<Integer> head = toList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		print(head);
		head = reverseDoublyLinkedList(head);
		print(head);
	}
}
