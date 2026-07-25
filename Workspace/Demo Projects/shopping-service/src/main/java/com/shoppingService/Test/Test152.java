package com.shoppingService.Test;

import java.util.Arrays;

public class Test152 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 0, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < arrayOfIntegers.length; s++) {
                minIndex = arrayOfIntegers[minIndex] < arrayOfIntegers[s] ? minIndex : s;
            }
            int temp = arrayOfIntegers[a];
            arrayOfIntegers[a] = arrayOfIntegers[minIndex];
            arrayOfIntegers[minIndex] = temp;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
