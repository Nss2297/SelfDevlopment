package com.shoppingService.interview.collectionframework.vector;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VectorWithFillRatio {
	private static final Logger log = LoggerFactory.getLogger(VectorWithFillRatio.class);
	
	public static void  main(String args[]) {
		Vector vector = new Vector<>(10, 5);
		log.info("{}", vector.capacity());
		for (int i = 0; i < 10; i++) {
			vector.addElement(i);
		}
		log.info("{}", vector.capacity());
		vector.addElement("a");
		log.info("{}", vector.capacity());
		log.info("{}", vector);
	}
}
