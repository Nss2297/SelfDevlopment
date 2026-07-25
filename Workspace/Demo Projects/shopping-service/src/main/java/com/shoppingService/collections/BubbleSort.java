package com.shoppingService.collections;

import java.util.Iterator;

public class BubbleSort {

	public static void main(String args[]) {
		int arry[] = { 4, 3, 6, 5, 1, 2 };
		int temp = 0;

		for (int i : arry) {
			System.out.println(i);
		}
		for (int a = 0; a < arry.length; a++) {
			for (int s = 0; s < arry.length - 1; s++) {
				if (arry[s] > arry[s + 1]) {
					temp = arry[s];
					arry[s] = arry[s + 1];
					arry[s + 1] = temp;
				}
			}
		}

		System.out.println("Array after sorting");
		for (int i : arry) {
			System.out.println(i);
		}
	}
}
