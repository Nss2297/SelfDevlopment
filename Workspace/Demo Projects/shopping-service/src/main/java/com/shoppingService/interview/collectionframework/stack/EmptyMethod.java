package com.shoppingService.interview.collectionframework.stack;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyMethod {
	private static final Logger log = LoggerFactory.getLogger(EmptyMethod.class);

	public static void main(String[] args) {
		Stack stack = new Stack<>();
		boolean stackIsEmpty = stack.empty();
		if (stackIsEmpty) {
			log.info("Stack is empty;inserting elements.");
			for (int i = 0; i < 10; i++) {
				Object obj = stack.push(i);
				log.info("{} pushed into stack.", obj);
			}
		}
		log.info("{}", stack);
	}
}
