package com.shoppingService.interview.collectionframework.concurrent.hashmap;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHashMapRemove {
 private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentHashMapRemove.class);
 
 public static void main(String args[]) {
	 ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
	 map.put(1, "A");
	 map.put(2, "B");
	 map.put(3, "C");
	 map.remove(1, "B");
	 map.remove(1, "A");
	 LOGGER.info("{}", map);
 }
}
