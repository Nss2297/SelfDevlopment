package com.shoppingService.Test;

import java.util.Arrays;

public class Test124 {
    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arrayOfIntegers));
                int key = 5;
//        int key = 100;
        System.out.println("Find number: " + key);
        int index = -1;
        for (int a = 0; a < arrayOfIntegers.length; a++) {
            index = arrayOfIntegers[a] == key ? a : index;
        }
        String message = index > -1 ? "Number at index " + index : "Number not found.";
        System.out.println(message);
    }
}
