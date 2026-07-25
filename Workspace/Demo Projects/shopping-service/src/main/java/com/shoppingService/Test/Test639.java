package com.shoppingService.Test;

import java.util.Arrays;

public class Test639 {
    private static void insertionSort(int[] array) {
        for (int a = 1; a < array.length; a++) {
            int key = array[a];
            int pnt = a - 1;
            while (pnt > -1 && key < array[pnt]) {
                array[pnt + 1] = array[pnt];
                --pnt;
            }
            array[++pnt] = key;
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{6, 7, 8, 1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        insertionSort(arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));


    }
}
