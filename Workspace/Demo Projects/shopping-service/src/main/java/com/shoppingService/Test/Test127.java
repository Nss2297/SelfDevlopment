package com.shoppingService.Test;

import java.util.Arrays;

public class Test127 {
    private static int[] populatArray(int size, int startIndex, int[] array) {
        int[] arrayOfIntegers = new int[size];
        for (int a = 0; a < size; a++) {
            arrayOfIntegers[a] = array[a + startIndex];
        }
        return arrayOfIntegers;
    }

    private static void merge(int low, int high, int median, int[] array) {
        int[] leftArray = populatArray(median - low + 1, low, array);
        int[] rightArray = populatArray(high - median, median + 1, array);
        int leftPnt = 0;
        int rightPnt = 0;
        int pnt = low;
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
        if (leftPnt < leftArray.length) {
            while (leftPnt < leftArray.length) {
                array[pnt] = leftArray[leftPnt];
                ++leftPnt;
                ++pnt;
            }
        }
        if (rightPnt < rightArray.length) {
            while (rightPnt < rightArray.length) {
                array[pnt] = rightArray[rightPnt];
                ++rightPnt;
                ++pnt;
            }
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
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        mergeSort(0, arrayOfIntegers.length - 1, arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));

    }
}
