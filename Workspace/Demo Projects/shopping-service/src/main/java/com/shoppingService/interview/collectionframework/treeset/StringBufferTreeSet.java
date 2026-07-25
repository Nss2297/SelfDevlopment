package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringBufferTreeSet {
	private static final Logger log = LoggerFactory.getLogger(StringBufferTreeSet.class);

	public static void main(String[] args) {
		TreeSet<StringBuffer> treeSet = new TreeSet<>(new StringBufferComparator());
		treeSet.add(new StringBuffer("F"));
		treeSet.add(new StringBuffer("A"));
		treeSet.add(new StringBuffer("G"));
		treeSet.add(new StringBuffer("E"));
		treeSet.add(new StringBuffer("C"));
		treeSet.add(new StringBuffer("D"));
		treeSet.add(new StringBuffer("B"));
		log.info("{}", treeSet);
	}
}
