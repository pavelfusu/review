package us.fusu.ds.stack;

import java.util.Stack;

public class MinimumStack<T extends Comparable<T>> {

	private Stack<T> main = new Stack<>();;
	private Stack<T> min = new Stack<>();

	public void pop() {
		T element = main.pop();
		if (element.equals(min.peek())) {
			min.pop();
		}
	}

	public void push(T element) {
		main.push(element);
		if (min.isEmpty()) {
			min.push(element);
		} else if (element.compareTo(min.peek()) < 0) {
			min.push(element);
		}
	}

	public T peek() {
		return main.peek();
	}

	public T minimum() {
		if (!min.isEmpty()) {
			return min.peek();
		}

		return null;
	}
	
	public static void main(String[] args) {
		MinimumStack<Integer> stack = new MinimumStack<>();
		stack.push(5);
		stack.push(2);
		stack.push(8);
		stack.push(5);
		stack.push(7);
		stack.push(13);
		stack.push(3);
		stack.push(11);
		stack.push(1);
		stack.push(6);
		
		stack.pop();
		stack.pop();
		System.out.println(stack.minimum());
	}
}
