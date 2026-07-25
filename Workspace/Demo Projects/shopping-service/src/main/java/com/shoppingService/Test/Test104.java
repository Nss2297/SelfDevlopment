package com.shoppingService.Test;

import java.util.Arrays;

public class Test104 {
    private static void swap(int leftPnt, int index, int[] array) {
        int temp = array[leftPnt];
        array[leftPnt] = array[index];
        array[index] = temp;
    }

    private static int partition(int low, int high, int[] array) {
        int leftPnt = low - 1;
        for (int a = low; a < high; a++) {
            if (array[a] < array[high]) {
                ++leftPnt;
                swap(leftPnt, a, array);
            }
        }
        swap(leftPnt + 1, high, array);
        return ++leftPnt;
    }

    private static void quickSort(int low, int high, int[] array) {
        if (low < high) {
            int pivotIndex = partition(low, high, array);
            quickSort(low, pivotIndex - 1, array);
            quickSort(++pivotIndex, high, array);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 8, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
