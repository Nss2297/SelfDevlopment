package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomizedSorting {

	private static final Logger log = LoggerFactory.getLogger(CustomizedSorting.class);

	public static void main(String args[]) {
		TreeSet<Integer> treeSet = new TreeSet<>(new Comparator1());
//		TreeSet<Integer> treeSet = new TreeSet<>();
		treeSet.add(10);
		treeSet.add(0);
		treeSet.add(15);
		treeSet.add(5);
		treeSet.add(20);
		treeSet.add(20);
		log.info("{}", treeSet);
	}
}
