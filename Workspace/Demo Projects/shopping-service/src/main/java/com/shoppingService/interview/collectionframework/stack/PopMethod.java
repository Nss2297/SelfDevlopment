package com.shoppingService.interview.collectionframework.stack;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopMethod {
	private static final Logger log = LoggerFactory.getLogger(PopMethod.class);

	public static void main(String[] args) {
		Stack stack = new Stack<>();
		for (int i = 0; i < 10; i++) {
			Object obj = stack.push(i);
			log.info("{} pushed into stack.", obj);
		}
		Object obj = stack.pop();
		log.info("{} poped out of stack.", obj);
		log.info("{}", stack);
	}

}
