package com.shoppingService.interview.collectionframework.concurrent.hashmap;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapProg1 {

	public static void main(String args[]) {
		ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.put(1, "A");
		concurrentHashMap.put(2, "B");
		concurrentHashMap.putIfAbsent(3, "C");
		concurrentHashMap.putIfAbsent(1, "D");
		concurrentHashMap.remove(1, "D");
		concurrentHashMap.replace(2, "B", "E");
		System.out.println(concurrentHashMap);
	}
}
