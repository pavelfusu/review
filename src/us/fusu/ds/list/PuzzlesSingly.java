package us.fusu.ds.list;

import us.fusu.ds.list.singly.Entry;
import us.fusu.io.IO;

public class PuzzlesSingly {
	
	public static <V> Entry<V> reverse(Entry<V> argHead) {
		Entry<V> previous = null;
		
		while (argHead != null) {
			Entry<V> next = argHead.next;
			argHead.next = previous;
			previous = argHead;
			argHead = next;
		}
		
		return previous;
	}
	
	public static <V> Entry<V> reverseRecursive(Entry<V> argHead, Entry<V> argPevious) {
		if (argHead == null) {
			return argHead;
		}
		
		Entry<V> next = argHead.next;
		argHead.next = argPevious;

		if (next.next == null) {
			next.next = argHead;
			return next;
		} else {
			return reverseRecursive(next, argHead);
		}
	}
	
	public static <V> Entry<V> reverseRecursive(Entry<V> argHead) {
		return reverseRecursive(argHead, null);
	}
	
	public static <V extends Comparable<V>> Entry<V> mergeSortedLists(Entry<V> root1, Entry<V> root2) {
		Entry<V> root = null;
		Entry<V> current = null;
		
		while (root1 != null && root2 != null) {
			Entry<V> next = root1.data.compareTo(root2.data) < 0 ? root1 : root2;
			
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
	
	public static <V> Entry<V> asList(V[] arr) {
		Entry<V> head = new Entry<>(arr[0]);
		Entry<V> current = head;
		for (int i = 1; i < arr.length; i++) {
			current.next = new Entry<>(arr[i]);
			current = current.next; 
		}
		
		return head;
	}
	
	public static <V> Entry<V> asCircularList(V[] arr) {
  	  Entry<V> head = new Entry<>(arr[0]);
      Entry<V> current = head;
      for (int i = 1; i < arr.length; i++) {
          current.next = new Entry<>(arr[i]);
          current = current.next; 
      }
      
      current.next = head;
      
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
	
	public static <V> void printCircular(Entry<V> someNode) {
      IO.write("[ ");
      Entry<V> initialNode = someNode;
      do {
          IO.write(someNode.data + " ");
          someNode = someNode.next;
      } while (initialNode != someNode);
      IO.writeLn("]");
  }
	
	public static <V> void deleteFromCircularList(Entry<V> argSomeNode, V argValueToDelete) {
	  Entry<V> initialNode = argSomeNode;
	  
	  if (argSomeNode == null) {
	    return;
	  }
	  
	  do {
	    if (argSomeNode.next == null) {
	      throw new IllegalArgumentException("That wasn't a circular list, liar!");
	    }
	    if (argSomeNode.next.data.equals(argValueToDelete)) {
	      Entry<V> next = argSomeNode.next;
	      argSomeNode.next = next.next;
	      next.next = null; // make it easy for GC
	      break;
	    }
	    
	    argSomeNode = argSomeNode.next;
	  } while (initialNode.next != argSomeNode.next); // while we don't encounter the inital node again
	}
	
	public static <V> boolean hasCycle(Entry<V> someNode) {
		if (someNode == null) {
			throw new IllegalArgumentException();
		}
		Entry<V> slow = someNode;
		Entry<V> fast = someNode;
		
		boolean cyclic = false;
		
		while (fast != null && slow != null && fast.next != null ) {
			slow = slow.next;
			fast = fast.next.next;
			
			if (slow == fast) {
				cyclic = true;
				break;
			}
		}
		
		return cyclic;
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
	  
		Entry<Integer> head = asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		Entry<Integer> tail = head;
		while (tail.next != null) {
			tail = tail.next;
		}
		tail.next = head.next.next.next.next;
		
		System.out.println(hasCycle(head));
	}
}
