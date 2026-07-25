package com.shoppingService.interview.collectionframework.linklist;

import java.util.Iterator;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IteratorLinkList {
	private static final Logger log = LoggerFactory.getLogger(IteratorLinkList.class);

	public static void main(String[] args) {
		LinkedList linkedList = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			linkedList.add(i);
			log.info("{} added into link list.", i);
		}

		Iterator<Object> itr = linkedList.iterator();
		while (itr.hasNext()) {
			log.info("{}", itr.next());
		}
	}
}
