package com.shoppingService.dsaPath.arrays.day2;

import java.util.Arrays;

public class ReverseAnArray {
	public static void main(String[] args) {
		int[] anArrayOfIntegers = new int[] { 3, 5, 9, 4, 2, 1 };
		System.out.println("Orignal Array: " + Arrays.toString(anArrayOfIntegers));
		int length = anArrayOfIntegers.length;
		for (int a = 0, s = length - 1; a < s; a++, --s) {
			int temp = anArrayOfIntegers[a];
			anArrayOfIntegers[a] = anArrayOfIntegers[s];
			anArrayOfIntegers[s] = temp;
		}
		System.out.println("Reversed Array: " + Arrays.toString(anArrayOfIntegers));
	}
}
