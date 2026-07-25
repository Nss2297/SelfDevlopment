package com.shoppingService.Test;

import java.util.Arrays;

public class Test116 {
    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < arrayOfIntegers.length; s++) {
                minIndex = arrayOfIntegers[minIndex] < arrayOfIntegers[s] ? minIndex : s;
            }
            swap(minIndex, a, arrayOfIntegers);
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
