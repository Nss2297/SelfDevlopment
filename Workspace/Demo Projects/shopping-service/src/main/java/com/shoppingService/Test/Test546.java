package com.shoppingService.Test;

import java.util.Arrays;

public class Test546 {
    private static void insertionSort(int[] array) {
        for (int a = 1; a < array.length; a++) {
            int pnt = a - 1;
            int key = array[a];
            while (pnt > -1 && key < array[pnt]) {
                array[pnt + 1] = array[pnt];
                --pnt;
            }
            array[++pnt] = key;
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 8, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        insertionSort(arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
