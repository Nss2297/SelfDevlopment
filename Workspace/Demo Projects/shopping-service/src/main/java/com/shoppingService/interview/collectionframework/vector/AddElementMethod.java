package com.shoppingService.interview.collectionframework.vector;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddElementMethod {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddElementMethod.class);

	public static void main(String args[]) {
		Vector vector = new Vector<>();
		for (int i = 0; i < 10; i++) {
			vector.addElement(i);
		}
		LOGGER.info("{}", vector.capacity());
		vector.addElement("a");
		LOGGER.info("{}", vector.capacity());
		LOGGER.info("{}", vector);
	}
}
