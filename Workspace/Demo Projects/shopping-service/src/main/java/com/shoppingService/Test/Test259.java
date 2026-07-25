package com.shoppingService.Test;

import java.util.Arrays;

public class Test259 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (num == array[median]) return median;
            if (num < array[median]) return binarySearch(low, median - 1, num, array);
            if (num > array[median]) return binarySearch(median + 1, high, num, array);
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(1);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 1, arrayOfIntegers));
        System.out.println(4);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 4, arrayOfIntegers));
        System.out.println(5);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 5, arrayOfIntegers));
        System.out.println(8);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 8, arrayOfIntegers));
        System.out.println(100);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 100, arrayOfIntegers));
        System.out.println(-100);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, -100, arrayOfIntegers));
    }
}
