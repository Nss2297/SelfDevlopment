package com.shoppingService.multithreading;

public class Test9 {
	public static void main(String args[]) throws InterruptedException {
		Test5 t1 = new Test5();
		Test5 t2 = new Test5();
		
		t1.setName("Thread1");
		t2.setName("Thread2");
		
		System.out.println(t1.isAlive());
		t1.start();
		System.out.println(t1.isAlive());
		System.out.println(t2.isAlive());
		t2.start();
		System.out.println(t2.isAlive());
	}
}
