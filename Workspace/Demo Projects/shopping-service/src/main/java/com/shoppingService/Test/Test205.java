package com.shoppingService.Test;

import java.util.Arrays;

public class Test205 {
    private static int linearSearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            for (int a = low; a < array.length; a++) {
                if (array[a] == num) return a;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int num = 6;
//        int num=100;
//        int num=-106;
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(linearSearch(0, arrayOfIntegers.length - 1, num, arrayOfIntegers));
    }
}
