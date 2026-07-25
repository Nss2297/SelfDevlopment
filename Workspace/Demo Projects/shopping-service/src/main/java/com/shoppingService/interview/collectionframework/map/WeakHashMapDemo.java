package com.shoppingService.interview.collectionframework.map;

import java.util.HashMap;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeakHashMapDemo {
	private static final Logger log = LoggerFactory.getLogger(WeakHashMapDemo.class);

	public static void main(String[] args) throws InterruptedException {

		log.info("HashMap dominates GarbageCollector.");
		HashMap<Object, String> hashMap = new HashMap<>();
		WeakHashMapTempModel hashMapTempModel1 = new WeakHashMapTempModel();
		hashMap.put(hashMapTempModel1, "HashMap");
		log.info("{}", hashMap);
		hashMapTempModel1 = null;
		System.gc();
		Thread.sleep(2000);
		log.info("{}", hashMap);

		log.info("GarbageCollector dominates WeakHashMap.");
		WeakHashMap<Object, String> weakHashMap = new WeakHashMap<>();
		WeakHashMapTempModel hashMapTempModel2 = new WeakHashMapTempModel();
		weakHashMap.put(hashMapTempModel2, "WeakHashMap");
		log.info("{}", weakHashMap);
		hashMapTempModel2 = null;
		System.gc();
		Thread.sleep(2000);
		log.info("{}", weakHashMap);
	}
}
