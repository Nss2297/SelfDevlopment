package com.shoppingService.multithreading;

import java.util.Iterator;

public class Test5 extends Thread{
	@Override
	public void run() {
		String a = Thread.currentThread().getName();
		for (int s=0;s<3;s++) {
			System.out.println(a);
		}
	}
}
