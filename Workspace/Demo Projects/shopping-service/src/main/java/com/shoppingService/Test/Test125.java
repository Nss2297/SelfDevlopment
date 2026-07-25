package com.shoppingService.Test;

import java.util.Arrays;

public class Test125 {
    private static int binarySearch(int low, int high, int key, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == key) {
                return median;
            } else if (array[median] < key) {
                return binarySearch(median + 1, high, key, array);
            } else {
                return binarySearch(low, median - 1, key, array);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
//        int key = 5;
        int key = 100;
        System.out.println("Find number: " + key);
        int index = binarySearch(0, arrayOfIntegers.length - 1, key, arrayOfIntegers);
        String message = index > -1 ? "Number at index " + index : "Number not found.";
        System.out.println(message);
    }
}
