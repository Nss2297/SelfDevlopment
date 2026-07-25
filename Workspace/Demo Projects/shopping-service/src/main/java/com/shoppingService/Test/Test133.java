package com.shoppingService.Test;

import java.util.Arrays;

public class Test133 {
    private static int partition(int low,int high,int[] array){
     return 0;
    }
    private static void quickSort(int low,int high,int[] array){
        int pivotIndex=partition(low,high,array);
    }
    public static void main(String[] args){
        int[] arrayOfIntegers = new int[]{5, 6, 2, 3, 1, 7, 4};
        System.out.println(Arrays.toString(arrayOfIntegers));
        quickSort(0,arrayOfIntegers.length-1,arrayOfIntegers);
        System.out.println(Arrays.toString(arrayOfIntegers));
    }
}
