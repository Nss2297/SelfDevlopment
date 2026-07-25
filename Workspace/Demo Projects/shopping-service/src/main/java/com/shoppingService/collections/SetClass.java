package com.shoppingService.collections;

import java.util.HashSet;
import java.util.Iterator;

public class SetClass {

	public static void main(String args[]) {
		HashSet hashSet = new HashSet<>();
		hashSet.add(null);
		hashSet.add(null);
		hashSet.add("");
		hashSet.add("");
		hashSet.add("sdfsd");
		System.out.println(hashSet.size());
		Iterator<String> iterator = hashSet.iterator();
		int index = 0;
		while(iterator.hasNext()) {
			String element = iterator.next();
			System.out.println(index +"-"+element);
			++index;
		}
	}
}
