package com.shoppingService.java11;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollectionToArray {
	public static void main(String args[]) {
		List<String> collection = Arrays.asList("One", "Two", "Three");
		log.info("List size: {}", collection.size());
		log.info("List elements: {}", collection);
		String[] array = collection.toArray(String[]::new);
		log.info("Array size: {}", array.length);
		log.info("Array elements:");
		for (String element : array) {
			log.info("{}", element);
		}
	}
}
