package com.shoppingService.Test;

import java.util.Arrays;

public class Test501 {
    private static void seletionSort(int[] array) {
        for (int a = 0; a < array.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < array.length; s++) {
                minIndex = array[minIndex] < array[s] ? minIndex : s;
            }
            int temp = array[minIndex];
            array[minIndex] = array[a];
            array[a] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 8, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        seletionSort(arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
