package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReverserAlpahbaticalOrderDemo {

	private static final Logger log = LoggerFactory.getLogger(ReverserAlpahbaticalOrderDemo.class);

	public static void main(String args[]) {
		TreeSet<String> treeSet = new TreeSet<>(new ReverseAlphabetComparator());
		treeSet.add("A");
		treeSet.add("F");
		treeSet.add("B");
		treeSet.add("E");
		treeSet.add("D");
		treeSet.add("C");
		log.info("{}", treeSet);
	}
}
