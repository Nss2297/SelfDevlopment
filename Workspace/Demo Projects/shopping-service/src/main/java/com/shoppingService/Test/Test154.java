package com.shoppingService.Test;

import java.util.Arrays;

public class Test154 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == num) return median;
            if (array[median] < num) return binarySearch(median + 1, high, num, array);
            if (array[median] > num) return binarySearch(low, median - 1, num, array);
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 5, arrayOfIntegers));
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 100, arrayOfIntegers));
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, -100, arrayOfIntegers));
    }
}
