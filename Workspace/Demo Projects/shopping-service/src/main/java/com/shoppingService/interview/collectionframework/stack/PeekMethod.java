package com.shoppingService.interview.collectionframework.stack;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeekMethod {
	private static final Logger log = LoggerFactory.getLogger(PeekMethod.class);

	public static void main(String[] args) {
		Stack stack = new Stack<>();
		for (int i = 0; i < 10; i++) {
			Object obj = stack.push(i);
			log.info("{} pushed into stack.", obj);
		}
		log.info("{}", stack);
		log.info("{} is the last inserted element.", stack.peek());
	}

}
