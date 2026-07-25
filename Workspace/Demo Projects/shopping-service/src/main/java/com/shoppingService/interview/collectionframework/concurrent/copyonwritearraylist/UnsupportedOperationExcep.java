package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnsupportedOperationExcep {
	public static void main(String args[]) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		list.add("ä");
		list.add("b");
		list.add("c");
		list.add("d");
		System.out.println(list);
		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			if (itr.next().equals("c")) {
				itr.remove();
			}
		}
		System.out.println(list);
	}
}
