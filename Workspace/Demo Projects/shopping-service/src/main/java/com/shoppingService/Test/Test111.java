package com.shoppingService.Test;

import java.util.Arrays;

public class Test111 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int index = 1; index < arrayOfIntegers.length; index++) {
            int pnt = index - 1;
            int key = arrayOfIntegers[index];
            while (pnt > -1 && arrayOfIntegers[pnt] > key) {
                arrayOfIntegers[pnt + 1] = arrayOfIntegers[pnt];
                --pnt;
            }
            arrayOfIntegers[++pnt] = key;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
