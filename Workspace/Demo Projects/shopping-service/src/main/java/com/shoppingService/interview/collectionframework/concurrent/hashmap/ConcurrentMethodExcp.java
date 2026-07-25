package com.shoppingService.interview.collectionframework.concurrent.hashmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentMethodExcp extends Thread {
	static List<String> list = new ArrayList();

	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list.add("A");
	}

	public static void main(String args[]) throws InterruptedException {
		list.add("B");
		list.add("C");
		list.add("D");

		ConcurrentMethodExcp t2 = new ConcurrentMethodExcp();
		t2.start();

		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
			Thread.sleep(3000);
		}
		
		System.out.println(list);
	}
}
