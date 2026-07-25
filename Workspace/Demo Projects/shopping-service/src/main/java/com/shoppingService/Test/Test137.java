package com.shoppingService.Test;

import java.util.Arrays;

public class Test137 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < arrayOfIntegers.length; s++) {
                minIndex = arrayOfIntegers[s] < arrayOfIntegers[minIndex] ? s : minIndex;
            }
            int temp = arrayOfIntegers[a];
            arrayOfIntegers[a] = arrayOfIntegers[minIndex];
            arrayOfIntegers[minIndex] = temp;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
