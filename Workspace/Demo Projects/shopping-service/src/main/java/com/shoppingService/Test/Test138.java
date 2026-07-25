package com.shoppingService.Test;

import java.util.Arrays;

public class Test138 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{0, 1, 2, 4, 5, 6, 7};
        int num = 6;
        System.out.println(Arrays.toString(arrayOfIntegers));
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            if (arrayOfIntegers[a] == num) {
                System.out.println(a);
                break;
            }
        }
    }
}
