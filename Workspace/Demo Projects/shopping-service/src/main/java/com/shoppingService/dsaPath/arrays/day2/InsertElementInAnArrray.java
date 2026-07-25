package com.shoppingService.dsaPath.arrays.day2;

import java.util.Arrays;

public class InsertElementInAnArrray {
	public static void main(String[] args) {
		int[] anArrayOfIntegers = new int[] { 3, 5, 9, 4, 2 };
		int[] newArrayOfIntegers = new int[anArrayOfIntegers.length + 1];
		int element = 10, position = 2;
		for (int a = 0; a < position; a++) {
			newArrayOfIntegers[a] = anArrayOfIntegers[a];
		}
		newArrayOfIntegers[2] = 10;
		for (int s = position; s < anArrayOfIntegers.length; s++) {
			newArrayOfIntegers[s + 1] = anArrayOfIntegers[s];
		}
		System.out.println("Original Array: " + Arrays.toString(anArrayOfIntegers));
		System.out.println("New Array: " + Arrays.toString(newArrayOfIntegers));
	}
}
