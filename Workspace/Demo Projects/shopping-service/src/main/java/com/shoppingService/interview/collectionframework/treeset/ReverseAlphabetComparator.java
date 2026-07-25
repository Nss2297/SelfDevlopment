package com.shoppingService.interview.collectionframework.treeset;

import java.util.Comparator;

public class ReverseAlphabetComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		String obj1 = (String) o1;
		String obj2 = (String) o2;
		return -obj1.compareTo(obj2);
	}

}
