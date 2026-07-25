package com.shoppingService.Test;


import java.util.Arrays;

public class Test99 {
    private static void swap(int[] array, int num1Index, int num2Index) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static int partition(int[] array, int low, int high) {
        int a = low-1;
        for (int s = low; s < high; s++) {
            if (array[s] < array[high]) {
                ++a;
                swap(array, a, s);
            }
        }
        swap(array, a + 1, high);
        return ++a;
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex-1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 2, 5, 1, 3, 6};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
