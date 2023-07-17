package ca.utoronto.utm.othello.viewcontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic implementation of a stack data structure backed by an array list
 */
class Stack<T> {
	private List<T> stack = new ArrayList<T>();

	/**
	 * Push an item to the stack
	 * 
	 * @param element
	 */
	public void push(T element) {
		stack.add(0, element);
	}

	/**
	 * Push an item to the stack
	 * 
	 * @param element
	 */
	public T peek() {
		return stack.get(0);
	}

	/**
	 * Get the last element of the stack
	 * 
	 * @return the last element
	 */
	public T pop() {
		return stack.remove(0);
	}

	/**
	 * 
	 * @return if stack is empty
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Method that empties the stack
	 */
	public void clear() {
		this.stack = new ArrayList<>();
	}

	/**
	 * Method that counts how many elements are in the stack
	 * 
	 * @return number of elements
	 */
	public int size() {
		return stack.size();
	}

}