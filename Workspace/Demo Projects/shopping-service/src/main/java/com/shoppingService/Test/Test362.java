package com.shoppingService.Test;

import java.util.Arrays;

public class Test362 {
    private static int linearSearch(int num, int[] arrray) {
        for (int a = 0; a < arrray.length; a++) {
            if (num == arrray[a]) return a;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(1);
        System.out.println(linearSearch(1, arrayOfIntegers));
        System.out.println(4);
        System.out.println(linearSearch(4, arrayOfIntegers));
        System.out.println(5);
        System.out.println(linearSearch(5, arrayOfIntegers));
        System.out.println(8);
        System.out.println(linearSearch(8, arrayOfIntegers));
        System.out.println(100);
        System.out.println(linearSearch(100, arrayOfIntegers));
        System.out.println(-100);
        System.out.println(linearSearch(-100, arrayOfIntegers));
    }
}
