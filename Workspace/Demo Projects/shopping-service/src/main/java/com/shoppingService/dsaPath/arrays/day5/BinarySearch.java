package com.shoppingService.dsaPath.arrays.day5;

import java.util.Arrays;

public class BinarySearch {
    private static int searchNumber(int[] array, int num) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == num) {
                return median;
            } else if (array[median] < num) {
                low = median + 1;
            } else {
                high = median - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
//        int num = 6;
//        int num = 100;
//        int num = 2;
        int num = -100;
//        int num = 0;
        System.out.println("Find number: " + num);
        int index = searchNumber(arrayOfIntegers, num);
        String message = index > -1 ? "Number at index " + index + "." : "Number not found.";
        System.out.println(message);
    }
}
