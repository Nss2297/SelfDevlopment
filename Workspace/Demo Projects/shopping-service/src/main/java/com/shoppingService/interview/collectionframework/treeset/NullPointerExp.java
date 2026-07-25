package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

public class NullPointerExp {

	public static void main(String[] args) {
		TreeSet treeSet = new TreeSet<>();
		treeSet.add(2);
		treeSet.add(null);
		treeSet.add(1);
		System.out.println(treeSet);
	}
}
