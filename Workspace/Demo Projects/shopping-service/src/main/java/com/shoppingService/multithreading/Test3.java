package com.shoppingService.multithreading;

public class Test3 extends Thread{

	@Override
	public void run() {
		String a = Thread.currentThread().getName();
		for (int s=0;s<3;s++) {
			System.out.println(a);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
