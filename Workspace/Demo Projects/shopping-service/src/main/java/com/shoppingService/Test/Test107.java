package com.shoppingService.Test;

import java.util.Arrays;

public class Test107 {
    private static void swap(int minNumIndex, int index, int[] array) {
        int temp = array[minNumIndex];
        array[minNumIndex] = array[index];
        array[index] = temp;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            int minNumIndex = a;
            for (int s = a + 1; s < arrayOfIntegers.length; s++) {
                minNumIndex = arrayOfIntegers[minNumIndex] > arrayOfIntegers[s] ? s : minNumIndex;
            }
            swap(minNumIndex, a, arrayOfIntegers);
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
