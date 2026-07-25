package com.shoppingService.dsaPath.arrays.day3And4;

import java.util.Arrays;

public class MergeSort {
    private static int[] copyValuesIntoArrays(int[] arrayOfIntegers, int size, int index) {
        int[] array = new int[size];
        for (int a = 0; a < array.length; a++) {
            array[a] = arrayOfIntegers[a + index];
        }
        return array;
    }

    private static void merge(int[] arrayOfIntegers, int leftIndex, int rightIndex, int median) {
        int leftArraySize = median - leftIndex + 1;
        int rightArraySize = rightIndex - median;
        int[] leftArray = copyValuesIntoArrays(arrayOfIntegers, leftArraySize, leftIndex);
        int[] rightArray = copyValuesIntoArrays(arrayOfIntegers, rightArraySize, median + 1);
        int s = 0;
        int d = 0;
        int f = leftIndex;
        while (s < leftArraySize && d < rightArraySize) {
            if (leftArray[s] < rightArray[d]) {
                arrayOfIntegers[f] = leftArray[s];
                ++s;
            } else {
                arrayOfIntegers[f] = rightArray[d];
                ++d;
            }
            ++f;
        }
//remaining elements
        while (s < leftArraySize) {
            arrayOfIntegers[f] = leftArray[s];
            ++s;
            ++f;
        }
        while (d < rightArraySize) {
            arrayOfIntegers[f] = rightArray[d];
            ++d;
            ++f;
        }
    }

    private static void mergeSort(int[] arrayOfIntegers, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int median = (leftIndex + rightIndex) / 2;
            mergeSort(arrayOfIntegers, leftIndex, median);
            mergeSort(arrayOfIntegers, median + 1, rightIndex);
            merge(arrayOfIntegers, leftIndex, rightIndex, median);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{3, 5, 1, 4, 6, 2, 11, 23, 56};
        System.out.println(Arrays.toString(arrayOfIntegers));
        mergeSort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
