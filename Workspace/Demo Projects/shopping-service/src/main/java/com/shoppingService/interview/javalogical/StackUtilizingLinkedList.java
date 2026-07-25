package com.shoppingService.interview.javalogical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackUtilizingLinkedList {

	private static final Logger log = LoggerFactory.getLogger(StackUtilizingLinkedList.class);

	Node top = null;

	static class Node {
		Object data;
		Node next;

		Node(Object data) {
			this.data = data;
			this.next = null;
		}
	}

	void pushOperation(Object value) {
		Node node = new Node(value);
		if (null == top) {
			top = node;
			return;
		}
		node.next = top;
		top = node;
	}

	Object popOperation() {
		if (null == top) {
			log.info("Stack is empty.");
			return -1;
		}
		top = top.next;
		return top.data;
	}

	Object peekOperation() {
		if (null == top) {
			log.info("Stack is empty.");
			return -1;
		}
		return top.data;
	}

	void searchOperation() {
		Node node = top;
		while (null != node) {
			log.info("[{}]", node.data);
			node = node.next;
		}
	}

	public static void main(String[] args) {
		log.info("Implementing stack utilizing linkedlist.");
		StackUtilizingLinkedList stack = new StackUtilizingLinkedList();
		log.info("Adding elements in the stack.");
		stack.pushOperation(1);
		stack.pushOperation("b");
		stack.pushOperation(3);
		stack.pushOperation("d");
		log.info("======================================================================");
		log.info("Listing all elements in the stack.");
		stack.searchOperation();
		log.info("======================================================================");
		log.info("Removing elements in the stack.");
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("Removed [{}] from the stack.", stack.popOperation());
		log.info("======================================================================");
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
