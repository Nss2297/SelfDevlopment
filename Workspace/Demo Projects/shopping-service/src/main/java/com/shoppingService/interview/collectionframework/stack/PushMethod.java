package com.shoppingService.interview.collectionframework.stack;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushMethod {
	private static final Logger log = LoggerFactory.getLogger(PushMethod.class);

	public static void main(String[] args) {
		Stack stack = new Stack<>();
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		log.info("{}", stack);
	}
}
