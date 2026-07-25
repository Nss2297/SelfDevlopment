package com.shoppingService.Test;

import java.util.Arrays;

public class Test131 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low < high) {
            int median = (low + high) / 2;
            if (array[median] == num) {
                return median;
            } else if (array[median] < num) {
                return binarySearch(median + 1, high, num, array);
            } else if (array[median] > num) {
                return binarySearch(low, median - 1, num, array);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
        int key = 4;
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, key, arrayOfIntegers));
    }
}
