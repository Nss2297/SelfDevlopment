package com.shoppingService.Test;

import java.util.Arrays;

public class Test153 {
    private static int linearSearch(int num, int[] array) {
        for (int a = 0; a < array.length; a++) {
            if (array[a] == num) {
                return a;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(linearSearch(5, arrayOfIntegers));
        System.out.println(linearSearch(-100, arrayOfIntegers));
        System.out.println(linearSearch(100, arrayOfIntegers));
    }
}
