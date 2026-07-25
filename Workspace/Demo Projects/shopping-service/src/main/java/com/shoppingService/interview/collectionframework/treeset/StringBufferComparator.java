package com.shoppingService.interview.collectionframework.treeset;

import java.util.Comparator;

public class StringBufferComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		String obj1 = o1.toString();
		String obj2 = o2.toString();
		return -obj1.compareTo(obj2);
	}

}
