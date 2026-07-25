package com.shoppingService.Test;

import java.util.Arrays;

public class Test119 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == num) {
                return median;
            } else if (array[low] <= num) {
                return binarySearch(low, median - 1, num, array);
            } else if (num <= array[high]) {
                return binarySearch(median + 1, high, num, array);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int[] arrayOfIntegers = new int[]{5, 6, 7, 1, 2, 3, 4};
//        int[] arrayOfIntegers = new int[]{4,5,6,7,0,1,2};
        int[] arrayOfIntegers = new int[]{1};
        System.out.println(Arrays.toString(arrayOfIntegers));
//                int num = 6;
//        int num = 100;
//        int num = 2;
//        int num = -100;
//        int num = 0;
//        int num = 3;
        int num = 1;
        int index = binarySearch(0, arrayOfIntegers.length - 1, num, arrayOfIntegers);
        String message = index > -1 ? "Number at index " + index : "Number not found.";
        System.out.println(message);
    }
}
