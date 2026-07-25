package com.shoppingService.Test;

import java.util.Arrays;

public class Test603 {
    private static int linearSearch(int num, int[] array) {
        for (int a = 0; a < array.length; a++) {
            if (array[a] == num) return a;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
//        int num = 1;
//        int num=4;
//        int num=5;
//        int num=8;
//        int num=100;
        int num=-100;
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(num);
        System.out.println(linearSearch(num, arrayOfIntegers));
    }
}
