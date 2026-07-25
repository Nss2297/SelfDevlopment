package com.shoppingService.Test;

import java.util.Arrays;

public class Test251 {
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
//        int num = 1;
//        int num = 4;
//        int num = 5;
//        int num = 8;
//        int num = 100;
        int num = -100;
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(num);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, num, arrayOfIntegers));
    }
}
