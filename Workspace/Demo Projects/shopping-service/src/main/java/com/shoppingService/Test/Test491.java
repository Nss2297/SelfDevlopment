package com.shoppingService.Test;

import java.util.Arrays;

public class Test491 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = low + (high - low) / 2;
            if (num == array[median]) return median;
            if (array[low] <= array[median]) {
                if (num >= array[low] && num <= array[median]) return binarySearch(low, median - 1, num, array);
                return binarySearch(median + 1, high, num, array);
            }
            if (array[high] >= array[median]) {
                if (num >= array[median] && num <= array[high]) return binarySearch(median + 1, high, num, array);
                return binarySearch(low, median - 1, num, array);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{6, 7, 8, 1, 2, 3, 4, 5};
//        int num = 6;
//        int num=1;
//        int num=2;
//        int num=5;
//        int num=100;
        int num = -100;
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(num);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, num, arrayOfIntegers));
    }
}
