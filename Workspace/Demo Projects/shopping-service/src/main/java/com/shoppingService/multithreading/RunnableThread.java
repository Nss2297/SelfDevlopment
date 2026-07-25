package com.shoppingService.multithreading;

public class RunnableThread implements Runnable {

	@Override
	public void run() {
		for (int a = 0; a < 5; a++) {
			System.out.println("Interface thread.");
		}
	}

}
