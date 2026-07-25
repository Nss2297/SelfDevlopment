package com.shoppingService.multithreading;

public class Test2 {
public static void main(String args[]) {
	RunnableThread runnableThread = new RunnableThread();
	Thread thread = new Thread(runnableThread);
	thread.start();
	for(int a=0;a<5;a++) {
		System.out.println("Main.");
	}
}
}
