package com.shoppingService.interview.collectionframework.concurrent.hashmap;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHashMapPutIfAbsentMethod {
	
	private static final Logger logger = LoggerFactory.getLogger(ConcurrentHashMapPutIfAbsentMethod.class);
	
	public static void main(String args[]) {
		ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
		map.put(1, "A");
		map.put(2, "B");
		map.put(3, "C");
		map.putIfAbsent(4, "D");
		map.putIfAbsent(4, "E");
		map.putIfAbsent(5, "E");
		map.putIfAbsent(5, "F");
		logger.info("{}", map);
	}

}
