package us.fusu.ds.list;

import us.fusu.ds.list.singly.Node;
import us.fusu.io.IO;

public class PuzzlesSingly {
	
	public static <V> Node<V> reverse(Node<V> argHead) {
		Node<V> previous = null;
		
		while (argHead != null) {
			Node<V> next = argHead.next;
			argHead.next = previous;
			previous = argHead;
			argHead = next;
		}
		
		return previous;
	}
	
	public static <V> Node<V> reverseRecursive(Node<V> argHead, Node<V> argPevious) {
		if (argHead == null) {
			return argHead;
		}
		
		Node<V> next = argHead.next;
		argHead.next = argPevious;

		if (next.next == null) {
			next.next = argHead;
			return next;
		} else {
			return reverseRecursive(next, argHead);
		}
	}
	
	public static <V> Node<V> reverseRecursive(Node<V> argHead) {
		return reverseRecursive(argHead, null);
	}
	
	public static <V extends Comparable<V>> Node<V> mergeSortedLists(Node<V> root1, Node<V> root2) {
		Node<V> root = null;
		Node<V> current = null;
		
		while (root1 != null && root2 != null) {
			Node<V> next = root1.data.compareTo(root2.data) < 0 ? root1 : root2;
			
			if (root == null) {
				root = next;
				current = next;
			} else {
				current.next = next;
				current = current.next;
			}
			
			if (next == root1) {
				root1 = root1.next;
			} else {
				root2 = root2.next;
			}
		}
		
		if (root1 != null) {
			current.next = root1;
		} else if (root2 != null) {
			current.next = root2;
		}
		
		return root;
	}
	
	public static <V> Node<V> asList(V[] arr) {
		Node<V> head = new Node<>(arr[0]);
		Node<V> current = head;
		for (int i = 1; i < arr.length; i++) {
			current.next = new Node<>(arr[i]);
			current = current.next; 
		}
		
		return head;
	}
	
	public static <V> Node<V> asCircularList(V[] arr) {
  	  Node<V> head = new Node<>(arr[0]);
      Node<V> current = head;
      for (int i = 1; i < arr.length; i++) {
          current.next = new Node<>(arr[i]);
          current = current.next; 
      }
      
      current.next = head;
      
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
	
	public static <V> void printCircular(Node<V> someNode) {
      IO.write("[ ");
      Node<V> initialNode = someNode;
      do {
          IO.write(someNode.data + " ");
          someNode = someNode.next;
      } while (initialNode != someNode);
      IO.writeLn("]");
  }
	
	public static <V> void deleteFromCircularList(Node<V> argSomeNode, V argValueToDelete) {
	  Node<V> initialNode = argSomeNode;
	  
	  if (argSomeNode == null) {
	    return;
	  }
	  
	  do {
	    if (argSomeNode.next == null) {
	      throw new IllegalArgumentException("That wasn't a circular list, liar!");
	    }
	    if (argSomeNode.next.data.equals(argValueToDelete)) {
	      Node<V> next = argSomeNode.next;
	      argSomeNode.next = next.next;
	      next.next = null; // make it easy for GC
	      break;
	    }
	    
	    argSomeNode = argSomeNode.next;
	  } while (initialNode.next != argSomeNode.next); // while we don't encounter the inital node again
	}

	public static void main(String[] args) {
//		Node<Integer> head = asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
//		print(head);
//		head = reverseRecursive(head);
//		print(head);
		
//		Node<Integer> l1 = asList(new Integer[] {6, 8, 12, 15, 20, 22});
//		Node<Integer> l2 = asList(new Integer[] {1, 3, 5, 7, 13, 16, 17, 21, 23});
//		
//		print(l1);
//		print(l2);
//		
//		print(mergeSortedLists(l1, l2));
	  
	    Node<Integer> head = asCircularList(new Integer[] {1, 3, 5, 7, 13, 16, 17, 21, 23});
	    deleteFromCircularList(head, 23);
	    printCircular(head);
	}
}
