package com.shoppingService.Test;

import java.util.Arrays;

public class Test106 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{4, 6, 2, 7, 1, 3, 5};
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int index = 1; index < arrayOfIntegers.length; index++) {
            int pnt = index - 1;
            int num = arrayOfIntegers[index];
            while (pnt > -1 && arrayOfIntegers[pnt] > num) {
                arrayOfIntegers[pnt + 1] = arrayOfIntegers[pnt];
                --pnt;
            }
            arrayOfIntegers[pnt + 1] = num;
        }
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
