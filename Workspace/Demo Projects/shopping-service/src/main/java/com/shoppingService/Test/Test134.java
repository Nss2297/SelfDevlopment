package com.shoppingService.Test;

import java.util.Arrays;

public class Test134 {
    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static int partition(int low, int high, int[] array) {
        int pivotIndex = low - 1;
        for (int a = low; a < high; a++) {
            if (array[a] < array[high]) {
                ++pivotIndex;
                swap(a, pivotIndex, array);
            }
        }
        swap(pivotIndex + 1, high, array);
        return ++pivotIndex;
    }

    private static void quickSort(int low, int high, int[] array) {
        if (low < high) {
            int pivotIndex = partition(low, high, array);
            quickSort(low, pivotIndex - 1, array);
            quickSort(pivotIndex + 1, high, array);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
