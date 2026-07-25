package com.shoppingService.multithreading;

import java.util.Iterator;

public class Test7 {
	public static void main(String args[]) throws InterruptedException {
		Test5 t1 = new Test5();
		Test5 t2 = new Test5();
		Test5 t3 = new Test5();
		
		t1.setName("Thread1");
		t2.setName("Thread2");
		t3.setName("Thread3");
		
		t1.start();
		t1.join();
		
		t2.start();
		t3.start();
	}
}
