package com.shoppingService.Test;

import java.util.Arrays;

public class Test112 {
    private static void swap(int minNumIndex, int index, int[] array) {
        int temp = array[minNumIndex];
        array[minNumIndex] = array[index];
        array[index] = temp;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int index = 0; index < arrayOfIntegers.length; index++) {
            int minNumIndex = index;
            for (int pnt = index + 1; pnt < arrayOfIntegers.length; pnt++) {
                minNumIndex = arrayOfIntegers[minNumIndex] < arrayOfIntegers[pnt] ? minNumIndex : pnt;
            }
            if (minNumIndex != index) {
                swap(minNumIndex, index, arrayOfIntegers);
            }
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
