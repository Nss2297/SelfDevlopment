package com.shoppingService.Test;

import java.util.Arrays;

public class Test260 {
    private static int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
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
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(6);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 6, arrayOfIntegers));
        System.out.println(1);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 1, arrayOfIntegers));
        System.out.println(2);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 2, arrayOfIntegers));
        System.out.println(5);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 5, arrayOfIntegers));
        System.out.println(100);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, 100, arrayOfIntegers));
        System.out.println(-100);
        System.out.println(binarySearch(0, arrayOfIntegers.length - 1, -100, arrayOfIntegers));
    }
}
