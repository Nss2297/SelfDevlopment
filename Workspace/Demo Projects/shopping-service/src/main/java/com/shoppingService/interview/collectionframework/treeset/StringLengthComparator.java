package com.shoppingService.interview.collectionframework.treeset;

import java.util.Comparator;

public class StringLengthComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		String obj1 = o1.toString();
		String obj2 = o2.toString();
		int obj1Lenght = obj1.length();
		int obj2Lenght = obj2.length();
		if (obj1Lenght < obj2Lenght) {
			return -1;
		} else if (obj1Lenght > obj2Lenght) {
			return 1;
		}
		return obj1.compareTo(obj2);
	}

}
