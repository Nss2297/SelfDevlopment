package com.shoppingService.Test;

import java.util.Arrays;

public class Test151 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 5, 3, 6, 7, 0, 1, 2};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 1; a < arrayOfIntegers.length; a++) {
            int key = arrayOfIntegers[a];
            int pnt = a - 1;
            while (pnt > -1 && key < arrayOfIntegers[pnt]) {
                arrayOfIntegers[pnt + 1] = arrayOfIntegers[pnt];
                --pnt;
            }
            arrayOfIntegers[pnt + 1] = key;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
