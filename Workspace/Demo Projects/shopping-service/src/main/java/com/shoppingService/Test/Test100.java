package com.shoppingService.Test;


import java.util.Arrays;

public class Test100 {
    private static int[] populateArray(int[] array, int size, int index) {
        int[] arrayOfIntegers = new int[size];
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            arrayOfIntegers[a] = array[a + index];
        }
        return arrayOfIntegers;
    }

    private static void merge(int[] array, int low, int high, int median) {
        int[] leftArray = populateArray(array, median - low + 1, low);
        int[] rightArray = populateArray(array, high - median, median + 1);
        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = low;
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] < rightArray[rightIndex]) {
                array[arrayIndex] = leftArray[leftIndex];
                ++leftIndex;
            } else {
                array[arrayIndex] = rightArray[rightIndex];
                ++rightIndex;
            }
            ++arrayIndex;
        }
        while (leftIndex < leftArray.length) {
            array[arrayIndex] = leftArray[leftIndex];
            ++leftIndex;
            ++arrayIndex;
        }
        while (rightIndex < rightArray.length) {
            array[arrayIndex] = rightArray[rightIndex];
            ++rightIndex;
            ++arrayIndex;
        }
    }

    private static void mergeSort(int[] array, int low, int high) {
        if (low < high) {
            int median = (low + high) / 2;
            mergeSort(array, low, median);
            mergeSort(array, median + 1, high);
            merge(array, low, high, median);
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 2, 5, 1, 3, 6};
        System.out.println(Arrays.toString(arrayOfIntegers));
        mergeSort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
