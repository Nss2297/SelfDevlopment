package com.shoppingService.interview.collectionframework.concurrent.copyonwritearraylist;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiThreadingProgram extends Thread {
	static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Modifying child thread.");
		copyOnWriteArrayList.add("c");
	}

	public static void main(String args[]) throws InterruptedException {
		copyOnWriteArrayList.add("a");
		copyOnWriteArrayList.add("b");
		MultiThreadingProgram t2 = new MultiThreadingProgram();
		t2.start();
		Iterator<String> itr = copyOnWriteArrayList.iterator();
		while (itr.hasNext()) {
			System.out.println("Adding in main thread:" + itr.next());
			t2.sleep(3000);
		}
		System.out.println(copyOnWriteArrayList);
	}
}
