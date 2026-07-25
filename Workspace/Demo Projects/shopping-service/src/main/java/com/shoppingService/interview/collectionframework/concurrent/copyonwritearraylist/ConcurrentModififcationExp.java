package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.ArrayList;
import java.util.Iterator;

public class ConcurrentModififcationExp extends Thread {
	static ArrayList<String> list = new ArrayList<>();

	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Modifying child thread.");
		list.add("c");
	}

	public static void main(String args[]) throws InterruptedException {
		list.add("a");
		list.add("b");
		ConcurrentModififcationExp t2 = new ConcurrentModififcationExp();
		t2.start();
		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			System.out.println("Adding in main thread:" + itr.next());
			t2.sleep(3000);
		}
		System.out.println(list);
	}
}
