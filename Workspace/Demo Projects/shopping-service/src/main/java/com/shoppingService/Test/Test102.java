package com.shoppingService.Test;

import java.util.Arrays;

public class Test102 {
    private static int[] populateArray(int startIndex, int size, int[] array) {
        int[] arrayOfIntegers = new int[size];
        for (int a = 0; a < array.length; a++) {
            arrayOfIntegers[a] = array[startIndex + a];
        }
        return arrayOfIntegers;
    }

    private static void merge(int low, int high, int median, int[] array) {
        int[] leftArray = populateArray(low, median - low + 1, array);
        int[] rightArray = populateArray(median + 1, high - median, array);
        int leftPnt = 0;
        int rightPnt = 0;
        int pnt = 0;
        while (leftPnt < leftArray.length && rightPnt < rightArray.length) {
            if (leftArray[leftPnt] < rightArray[rightPnt]) {
                array[pnt] = leftArray[leftPnt];
                ++leftPnt;
            } else {
                array[pnt] = rightArray[rightPnt];
                ++rightPnt;
            }
            ++pnt;
        }
        while (leftPnt < leftArray.length) {
            array[pnt] = leftArray[leftPnt];
            ++leftPnt;
            ++pnt;
        }
        while (rightPnt < rightArray.length) {
            array[pnt] = rightArray[rightPnt];
            ++rightPnt;
            ++pnt;
        }
    }

    private static void mergeSort(int low, int high, int[] array) {
        if (low < high) {
            int median = (low + high) / 2;
            mergeSort(low, median, array);
            mergeSort(median + 1, high, array);
            merge(low, high, median, array);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 2, 5, 1, 3, 6};
        System.out.println(Arrays.toString(arrayOfIntegers));
        mergeSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
