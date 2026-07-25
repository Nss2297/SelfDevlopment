package com.shoppingService.dsaPath.arrays.day3And4;

import java.util.Arrays;

public class QuickSort {
	private static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	private static int partition(int[] array, int low, int high) {
		int pivot = array[high];
		int a = low - 1;
		for (int s = low; s < high; s++) {
			if (array[s] < pivot) {
				++a;
				swap(array, a, s);
			}
		}
		swap(array, a + 1, high);
		return a + 1;
	}

	private static void quickSort(int[] array, int low, int high) {
		if (low < high) {
			int pivotIndex = partition(array, low, high);
			quickSort(array, low, pivotIndex - 1);
			quickSort(array, pivotIndex + 1, high);
		}
	}

	public static void main(String[] args) {
		int[] arrayOfIntegers = new int[] { 5, 6, 2, 3, 1, 8, 4 };
		System.out.println(Arrays.toString(arrayOfIntegers));
		quickSort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
		System.out.println(Arrays.toString(arrayOfIntegers));
	}
}
