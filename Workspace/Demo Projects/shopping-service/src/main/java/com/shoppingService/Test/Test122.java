package com.shoppingService.Test;

import java.util.Arrays;

public class Test122 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 6, 2, 7, 1, 3, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int index = 1; index < arrayOfIntegers.length; index++) {
            int key = arrayOfIntegers[index];
            int pnt = index - 1;
            while (pnt > -1 && arrayOfIntegers[pnt] > key) {
                arrayOfIntegers[pnt+1] = arrayOfIntegers[pnt];
                --pnt;
            }
            arrayOfIntegers[++pnt] = key;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
