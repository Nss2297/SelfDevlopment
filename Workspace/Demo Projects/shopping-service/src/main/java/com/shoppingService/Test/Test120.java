package com.shoppingService.Test;

import java.util.Arrays;

public class Test120 {
    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static int partition(int low, int high, int[] array) {
        int pnt = low - 1;
        for (int a = low; a < high; a++) {
            if (array[a] < array[high]) {
                ++pnt;
                swap(pnt, a, array);
            }
        }
        swap(pnt + 1, high, array);
        return ++pnt;
    }

    private static void quickSort(int low, int high, int[] array) {
        if (low < high) {
            int pivotIndex = partition(low, high, array);
            quickSort(low, pivotIndex - 1, array);
            quickSort(pivotIndex + 1, high, array);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 6, 2, 7, 1, 3, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
