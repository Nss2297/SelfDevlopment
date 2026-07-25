package com.shoppingService.Test;

import java.util.Arrays;

public class Test109 {
	private static void swap(int num1Index, int num2Index, int[] array) {
		int temp = array[num1Index];
		array[num1Index] = array[num2Index];
		array[num2Index] = temp;
	}

	private static int partition(int low, int high, int[] array) {
		int a = low - 1;
		for (int s = low; s < high; s++) {
			if (array[s] < array[high]) {
				++a;
				swap(a, s, array);
			}
			swap(a + 1, high, array);
		}
		return ++a;
	}

	private static void quickSort(int low, int high, int[] array) {
		if (low < high) {
			int pivotIndex = partition(low, high, array);
			quickSort(low, pivotIndex, array);
			quickSort(pivotIndex + 1, high, array);
		}
	}

	public static void main(String[] args) {
		int[] arrayOfIntegers = new int[] { 5, 6, 2, 3, 1, 7, 4 };
		System.out.println(Arrays.toString(arrayOfIntegers));
		quickSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
		System.out.println(Arrays.toString(arrayOfIntegers));
	}
}
