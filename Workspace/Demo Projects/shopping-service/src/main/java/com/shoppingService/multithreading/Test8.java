package com.shoppingService.multithreading;

public class Test8 extends Thread{
	public void run() {
		String a = Thread.currentThread().getName();
		for(int s=0;s<3;s++) {
			System.out.println(a);
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		Test5 t1 = new Test5();
		Test5 t2 = new Test5();
		Test5 t3 = new Test5();
		
		t1.setName("Thread1");
		t2.setName("Thread2");
		t3.setName("Thread3");
		
		t1.start();
		t2.start();
		t3.start();
		t2.stop();
	}
}
