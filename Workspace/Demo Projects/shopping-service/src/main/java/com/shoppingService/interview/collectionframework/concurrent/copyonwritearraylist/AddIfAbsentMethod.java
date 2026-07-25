package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.concurrent.CopyOnWriteArrayList;

public class AddIfAbsentMethod {
public static void main(String arsg[]) {
	CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
	list.add("Ä");
	list.add("Ä");
	list.add("B");
	System.out.println(list);
	list.addIfAbsent("B");
	System.out.println(list);
}
}
