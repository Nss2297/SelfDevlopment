package com.shoppingService.Test;

import java.util.Arrays;

public class Test123 {
    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 6, 2, 7, 1, 3, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < arrayOfIntegers.length; s++) {
                minIndex = arrayOfIntegers[s] < arrayOfIntegers[minIndex] ? s : minIndex;
            }
            swap(a, minIndex, arrayOfIntegers);
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
