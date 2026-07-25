package com.shoppingService.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Test520 {
    private static int calculateSum(int endIndex, int[] array) {
        int sum = 0;
        for (int a = 0; a <= endIndex; a++) {
            sum += array[a];
        }
        return sum;
    }

    private static int[] populateArray(int high, int[] orignalArray) {
        int[] array = new int[high + 1];
        for (int a = 0; a <= high; a++) {
            array[a] = orignalArray[a];
        }
        return array;
    }

    private static int maxSubArray(int high, int[] array, TreeMap<Integer, int[]> map) {
        if (high > 0) {
            int[] subArray = populateArray(high, array);
            int sum = calculateSum(high, subArray);
            map.put(sum, subArray);
            maxSubArray(high - 1, array, map);
        }
        Map.Entry<Integer, int[]> lastEntry = map.lastEntry();
        System.out.println(Arrays.toString(lastEntry.getValue()));
        return lastEntry.getKey();
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        int[] arrayOfIntegers = new int[]{1};
//        int[] arrayOfIntegers = new int[]{5,4,-1,7,8};
        TreeMap<Integer, int[]> map = new TreeMap<>();
        System.out.println(Arrays.toString(arrayOfIntegers));
        System.out.println(maxSubArray(arrayOfIntegers.length - 1, arrayOfIntegers, map));
    }
}
