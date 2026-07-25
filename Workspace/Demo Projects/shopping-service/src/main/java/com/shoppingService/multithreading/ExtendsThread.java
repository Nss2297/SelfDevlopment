package com.shoppingService.multithreading;

public class ExtendsThread extends Thread{

	public void run() {
		for(int a=0;a<5;a++) {
			System.out.println("Thread.");
		}
	}
}

