package com.shoppingService.dsaPath.arrays.day3And4;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 6, 2, 7, 1, 3, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 1; a < arrayOfIntegers.length; a++) {
            int s = a - 1;
            int key = arrayOfIntegers[a];
            while (s > -1 && arrayOfIntegers[s] > key) {
                arrayOfIntegers[s + 1] = arrayOfIntegers[s];
                --s;
            }
            arrayOfIntegers[s + 1] = key;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
