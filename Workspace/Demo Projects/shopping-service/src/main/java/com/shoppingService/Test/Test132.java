package com.shoppingService.Test;

import java.util.Arrays;

public class Test132 {
    private static boolean isInSortedArray(int num1, int num2, int[] array) {
        return array[num1] <= array[num2];
    }

    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == num) return median;
            if (isInSortedArray(low, median, array)) {
                if (array[low] <= num && num <= array[median]) return binarySearch(low, median - 1, num, array);
                return binarySearch(median + 1, high, num, array);
            } else {
                if (array[median] <= num && num <= array[high]) return binarySearch(median + 1, high, num, array);
                return binarySearch(low, median - 1, num, array);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int[] arrayOfIntegers = new int[]{4, 5, 6, 7, 0, 1, 2};
//        int[] arrayOfIntegers = new int[]{1};
        int[] arrayOfIntegers = new int[]{1, 3};
        int key = 2;
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, key, arrayOfIntegers));
    }
}
