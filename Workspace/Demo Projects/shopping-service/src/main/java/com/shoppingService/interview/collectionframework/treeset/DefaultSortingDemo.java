package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSortingDemo {

	private static final Logger log = LoggerFactory.getLogger(DefaultSortingDemo.class);
	
	public static void main(String[] args) {
		TreeSet<String> treeSet = new TreeSet<>();
		treeSet.add("E");
		treeSet.add("A");
		treeSet.add("D");
		treeSet.add("C");
		treeSet.add("B");
		log.info("{}", treeSet);
		System.err.println(treeSet);
	}
}
