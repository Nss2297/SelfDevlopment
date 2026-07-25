package com.shoppingService.dsaPath.arrays.day3And4;

import java.util.Arrays;

public class SelectionSort {
    private static void swap(int minIndex, int index, int[] array) {
        int temp = array[index];
        array[index] = array[minIndex];
        array[minIndex] = temp;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 8, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        int size = arrayOfIntegers.length;
        for (int a = 0; a < size; a++) {
            int minIndex = a;
            for (int s = a + 1; s < size; s++) {
                minIndex = arrayOfIntegers[minIndex] < arrayOfIntegers[s] ? minIndex : s;
            }
            swap(minIndex, a, arrayOfIntegers);
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
