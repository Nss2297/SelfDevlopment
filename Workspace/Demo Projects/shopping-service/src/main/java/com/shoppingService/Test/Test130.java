package com.shoppingService.Test;

import java.util.Arrays;

public class Test130 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
        int key = 4;
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            if (arrayOfIntegers[a] == key) {
                System.out.println(a);
                break;
            }
        }
    }
}
