package com.shoppingService.interview.collectionframework.vector;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapacityMethod {
	private static final Logger log = LoggerFactory.getLogger(CapacityMethod.class);

	public static void main(String args[]) {
		Vector vector = new Vector<>(24);
		log.info("{}", vector.capacity());
		for (int i = 0; i < 10; i++) {
			vector.addElement(i);
		}
		vector.addElement("a");
		log.info("{}", vector.capacity());
		log.info("{}", vector);
	}
}
