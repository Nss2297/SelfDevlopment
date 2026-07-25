package com.shoppingService.Test;

import java.util.Arrays;

public class Test128 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 1; a < arrayOfIntegers.length; a++) {
            int pnt = a - 1;
            int key = arrayOfIntegers[a];
            while (pnt > -1 && arrayOfIntegers[pnt] > key) {
                arrayOfIntegers[pnt + 1] = arrayOfIntegers[pnt];
                --pnt;
            }
            arrayOfIntegers[pnt + 1] = key;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
