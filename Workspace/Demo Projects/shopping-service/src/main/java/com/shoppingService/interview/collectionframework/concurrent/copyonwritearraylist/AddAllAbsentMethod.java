package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddAllAbsentMethod {
	public static void main(String args[]) {
		ArrayList<String> list1 = new ArrayList<>();
		list1.add("A");
		list1.add("B");
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		list.add("A");
		list.add("C");
		list.addAll(list1);
		System.out.println(list);
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("A");
		list2.add("D");
		list.addAllAbsent(list2);
		System.out.println(list);
	}
}
