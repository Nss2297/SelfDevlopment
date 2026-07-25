package com.shoppingService.interview.collectionframework.linklist;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddElementsMethods {
	private static final Logger log = LoggerFactory.getLogger(AddElementsMethods.class);

	public static void main(String[] args) {
		LinkedList<Integer> linkedList = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			linkedList.add(i);
		}
		log.info("Link list after invoking add(E e) method: {}", linkedList);

		log.info("Add element at specific location");
		linkedList.add(2, 10);
		log.info("Link list after invoking add(int index, E e) method: {}", linkedList);

		log.info("Add collection to LinkList.");
		LinkedList<Integer> linkedList2 = new LinkedList<>();
		for (int i = 11; i <= 12; i++) {
			linkedList2.add(i);
		}
		linkedList.addAll(linkedList2);
		log.info("Link list after invoking addAll(Collection<? extends E> c) method: {}", linkedList);

		log.info("Add collection to specific index in LinkList.");
		LinkedList<Integer> linkedList3 = new LinkedList<>();
		for (int i = 13; i <= 14; i++) {
			linkedList3.add(i);
		}
		linkedList.addAll(3, linkedList3);
		log.info("Link list after invoking addAll(int index, Collection<? extends E> c) method: {}", linkedList);

	}
}
