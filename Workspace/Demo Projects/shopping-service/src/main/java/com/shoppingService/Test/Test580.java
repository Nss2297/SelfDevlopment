package com.shoppingService.Test;

import java.util.Arrays;

public class Test580 {
    private static void selectionSort(int[] array) {
        for (int a = 0; a < array.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < array.length; s++) {
                minIndex = array[s] < array[minIndex] ? s : minIndex;
            }
            int temp = array[a];
            array[a] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 8, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        selectionSort(arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
