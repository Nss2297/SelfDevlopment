package com.shoppingService.interview.javalogical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackUtilizingArray {

	private static final Logger log = LoggerFactory.getLogger(StackUtilizingArray.class);
	private String[] stack;
	private int capacity;
	private int index = 0;
	private int topIndex = -1;

	public StackUtilizingArray(int capacity) {
		super();
		this.capacity = capacity;
		this.stack = new String[capacity];
	}

	void pushOperation(Object value) {
		if (isFull()) {
			log.info("Stack is full.");
			return;
		}
		stack[index] = (String) value;
		if (index < capacity - 1) {
			++index;
		}
		topIndex = index;
	}

	Object popOperation() {
		if (!isEmpty()) {
			String element = stack[index];
			stack[index] = null;
			--index;
			topIndex = index;
			return element;
		}
		topIndex = index;
		return -1;
	}

	Object peekOperation() {
		if (-1 == index || null == stack) {
			log.info("Stack is empty.");
			return -1;
		}
		return stack[topIndex];
	}

	void listingOperation() {
		for (int arrayIndex = topIndex; arrayIndex > -1; arrayIndex--) {
			log.info("[{}]", stack[arrayIndex]);
		}
	}

	int[] searchOperation(Object element) {
		int[] indexes = new int[capacity];
		int tempIndex = 0;
		for (int searchIndex = topIndex; searchIndex > -1; searchIndex--) {
			if (stack[searchIndex] == element) {
				indexes[tempIndex] = searchIndex;
				++tempIndex;
			}
		}
		return indexes;
	}

	boolean isFull() {
		if (null != stack && 4 == index) {
			log.info("Stack is not empty.");
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	boolean isEmpty() {
		if (null == stack || -1 == index) {
			log.info("Stack is empty.");
			index = -1;
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static void main(String[] args) {
		log.info("Implementing stack utilizing Array.");
		log.info("====================================");
		StackUtilizingArray stack = new StackUtilizingArray(4);
		String thirdElement = "3";
		log.info("Adding elements in the stack.");
		stack.pushOperation("1");
		stack.pushOperation("b");
		stack.pushOperation(thirdElement);
		stack.pushOperation("d");
		log.info("====================================");
		log.info("Listing all elements in the stack.");
		stack.listingOperation();
		log.info("====================================");
		log.info("Element [{}] is at the following indexes in the stack {}.", thirdElement,
				stack.searchOperation(thirdElement));
		log.info("====================================");
		log.info("Removing elements in the stack.");
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("====================================");
		log.info("[{}] is at the top of the stack.", stack.peekOperation());

//		Stack stack = new Stack();
//		stack.add(1);
//		stack.add(2);
//		stack.add(3);
//		stack.add(4);
//		log.info("Elements in stack: {}", stack);
//		Enumeration<Object> cursor = stack.elements();
//		while(cursor.hasMoreElements()) {
//			log.info("[{}]", cursor.nextElement());
//		}
//		log.info("{}", stack.peek());
	}
}
