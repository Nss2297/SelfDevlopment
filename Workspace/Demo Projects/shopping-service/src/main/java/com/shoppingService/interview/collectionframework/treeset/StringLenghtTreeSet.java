package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringLenghtTreeSet {
	private static final Logger log = LoggerFactory.getLogger(StringLenghtTreeSet.class);

	public static void main(String[] args) {
		TreeSet treeSet = new TreeSet<>(new StringLengthComparator());
		treeSet.add("A");
		treeSet.add(new StringBuffer("ABC"));
		treeSet.add(new StringBuffer("AA"));
		treeSet.add("XX");
		treeSet.add("ABCD");
		treeSet.add("A");
		log.info("{}", treeSet);
	}
}
