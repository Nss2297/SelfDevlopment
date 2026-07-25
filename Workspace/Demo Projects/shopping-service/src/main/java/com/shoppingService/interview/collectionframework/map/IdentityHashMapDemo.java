package com.shoppingService.interview.collectionframework.map;

import java.util.HashMap;
import java.util.IdentityHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shoppingService.interview.collectionframework.vector.VectorWithFillRatio;

public class IdentityHashMapDemo {

	private static final Logger log = LoggerFactory.getLogger(VectorWithFillRatio.class);

	public static void main(String[] args) {
		Integer index1 = new Integer(10);
		Integer index2 = new Integer(10);
		HashMap<Integer, String> hashMap = new HashMap<>();
		hashMap.put(index1, "Raj");
		hashMap.put(index2, "Suresh");
		IdentityHashMap<Integer, String> identityHashMap = new IdentityHashMap<>();
		identityHashMap.put(index1, "Raj");
		identityHashMap.put(index2, "Suresh");
		log.info("{}", index1.equals(index2));
		log.info("{}", index1 == index2);
		log.info("HashMap:- {}", hashMap);
		log.info("IdentityHashMap:- {}", identityHashMap);
	}
}
