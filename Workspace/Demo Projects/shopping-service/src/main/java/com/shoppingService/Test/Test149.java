package com.shoppingService.Test;

import java.util.Arrays;

public class Test149 {
    private static void swap(int num1Index, int num2Index, int[] arrray) {
        int temp = arrray[num1Index];
        arrray[num1Index] = arrray[num2Index];
        arrray[num2Index] = temp;
    }

    private static int partition(int low, int high, int[] array) {
        int pnt = low - 1;
        int pivot = array[high];
        for (int a = low; a < high; a++) {
            if (array[a] < pivot) {
                ++pnt;
                swap(a, pnt, array);
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
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 0, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
