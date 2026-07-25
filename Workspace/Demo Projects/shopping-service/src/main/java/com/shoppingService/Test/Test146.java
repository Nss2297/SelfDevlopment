package com.shoppingService.Test;

import java.util.Arrays;

public class Test146 {
    private static int binarySearch(int low, int high, int[] array, int num) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (num == array[median]) return median;
            if (num < array[median]) return binarySearch(low, median - 1, array, num);
            if (num > array[median]) return binarySearch(median + 1, high, array, num);
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
//        int num = 4;
//        int num = 400;
        int num = -400;
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, arrayOfIntegers, num));
    }
}
