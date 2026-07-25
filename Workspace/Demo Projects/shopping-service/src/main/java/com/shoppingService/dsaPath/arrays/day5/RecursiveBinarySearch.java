package com.shoppingService.dsaPath.arrays.day5;

import java.util.Arrays;

public class RecursiveBinarySearch {
    private static int binarySearch(int num, int low, int high, int[] array) {
        int index = -1;
        if (low <= high) {
            int median = (low + high) / 2;
            if (num == array[median]) {
                return median;
            } else if (array[median] < num) {
                index = binarySearch(num, ++median, high, array);
            } else {
                index = binarySearch(num, low, --median, array);
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
//        int num = 6;
//        int num = 100;
//        int num = 2;
//        int num = -100;
        int num = 0;
        System.out.println("Find number: " + num);
        int index = binarySearch(num, 0, arrayOfIntegers.length - 1, arrayOfIntegers);
        String message = index > -1 ? "Number at index " + index + "." : "Number not found.";
        System.out.println(message);
    }
}
