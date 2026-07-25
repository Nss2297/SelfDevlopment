package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Program3 {
	public static void main(String args[]) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		Iterator<String> itr = list.iterator();
		list.add("d");
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
		System.out.println(list);
	}
}
