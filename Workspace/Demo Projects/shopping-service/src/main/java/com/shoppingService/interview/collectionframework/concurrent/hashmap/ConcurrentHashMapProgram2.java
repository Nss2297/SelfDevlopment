package com.shoppingService.interview.collectionframework.concurrent.hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHashMapProgram2 extends Thread {

//	static ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
	static HashMap concurrentHashMap = new HashMap();
	public static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentHashMapProgram2.class);

	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		LOGGER.info("Child thread updating collection");
		concurrentHashMap.put(3, "C");
	}

	public static void main(String args[]) throws InterruptedException {
		concurrentHashMap.put(1, "A");
		concurrentHashMap.put(2, "B");

		ConcurrentHashMapProgram2 t2 = new ConcurrentHashMapProgram2();
		t2.start();

		Set keys = concurrentHashMap.keySet();
		Iterator<Integer> itr = keys.iterator();
		while (itr.hasNext()) {
			int index = itr.next();
			LOGGER.info("Main thread is iterating, and current entry is:[{}]........[{}]", index,
					concurrentHashMap.get(index));
			Thread.sleep(3000);
		}
		LOGGER.info("{}", concurrentHashMap);
	}
}
