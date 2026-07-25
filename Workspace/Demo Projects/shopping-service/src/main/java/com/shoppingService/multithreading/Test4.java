package com.shoppingService.multithreading;

import java.util.Iterator;

public class Test4 {
public static void main(String args[]) {
	Test3 t1 = new Test3();
	Test3 t2 = new Test3();
	Test3 t3 = new Test3();
	t1.setName("Thread1");
	t2.setName("Thread2");
	t3.setName("Thread3");
	t1.start();
	t2.start();
	t3.start();
}
}
