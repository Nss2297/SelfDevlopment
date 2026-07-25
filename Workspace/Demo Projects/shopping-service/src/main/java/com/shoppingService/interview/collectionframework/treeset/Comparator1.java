package com.shoppingService.interview.collectionframework.treeset;

import java.util.Comparator;

public class Comparator1 implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		Integer obj1 = (Integer) o1;
		Integer obj2 = (Integer) o2;
		if (obj1 < obj2) {
			return 1;
		} else if (obj1 > obj2) {
			return -1;
		}
		return 0;
	}

}
