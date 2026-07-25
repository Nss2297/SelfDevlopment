package com.shoppingService.dsaPath.arrays.day5;

import java.util.Arrays;

public class LeetCodeQuestion33SearchInRotatedSortedArray {
    private int binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = (low + high) / 2;
            if (array[median] == num) {
                return median;
            }
            if (array[low] <= array[median]) {
                if (num >= array[low] && num <= array[median]) {
                    return binarySearch(low, median - 1, num, array);
                } else {
                    return binarySearch(median + 1, high, num, array);
                }
            } else {
                if (num >= array[median] && num <= array[high]) {
                    return binarySearch(median + 1, high, num, array);
                } else {
                    return binarySearch(low, median - 1, num, array);
                }
            }
        }
        return -1;
    }

    private int search(int[] nums, int target) {
        return binarySearch(0, nums.length - 1, target, nums);
    }

    public static void main(String[] args) {
        LeetCodeQuestion33SearchInRotatedSortedArray searchInRotatedSortedArray = new LeetCodeQuestion33SearchInRotatedSortedArray();
        int[] arrayOfIntegers = new int[]{4, 5, 6, 7, 0, 1, 2};
//        int[] arrayOfIntegers = new int[]{1};
//        int[] arrayOfIntegers = new int[]{1, 3};
        System.out.println(Arrays.toString(arrayOfIntegers));
//        int num = 4;
        int num = 0;
//        int num = 3;
        System.out.println("Find number: " + num);
        System.out.println(searchInRotatedSortedArray.search(arrayOfIntegers, num));
    }
}
