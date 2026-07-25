package com.shoppingService.LeetCode.Arrays.Problem_34;

import java.util.Arrays;

public class FindFirstAndLastPositionOfElementInSortedArray {
    private static int startIndex = Integer.MAX_VALUE;
    private static int endIndex = Integer.MIN_VALUE;

    public static void main(String[] args) {
        int[][] arrays = {{5, 7, 7, 8, 8, 10}, {1}, {2, 2}, {1, 3}, {1, 4}, {3, 3, 3}, {1, 2, 3, 3, 3, 3, 4, 5, 9}};
        int[][] nums = {{8, 6}, {1}, {2}, {1}, {4}, {3}, {3}};
        for (int a = 0; a < nums.length; a++) {
            int[] numArray = nums[a];
            int[] array = arrays[a];
            for (int s = 0; s < numArray.length; s++) {
                int num = numArray[s];
                System.out.println("Num: " + num + "\t Array: " + Arrays.toString(array) + "\tFirst and last position: " + Arrays.toString(searchRange(array, num)));
            }
        }
    }

    private static int[] searchRange(int[] nums, int target) {
        if (null == nums || nums.length < 0) return new int[]{-1, -1};
        if (nums.length == 1 && target == nums[0]) return new int[]{0, 0};
        binarySearch(0, nums.length - 1, target, nums);
        startIndex = startIndex != Integer.MAX_VALUE ? startIndex : -1;
        endIndex = endIndex != Integer.MIN_VALUE ? endIndex : -1;
        int[] array = new int[]{startIndex, endIndex};
        startIndex = Integer.MAX_VALUE;
        endIndex = Integer.MIN_VALUE;
        return array;
    }

    private static void binarySearch(int low, int high, int num, int[] array) {
        if (low <= high) {
            int median = low + (high - low) / 2;
            if (num == array[median]) {
                startIndex = median < startIndex ? median : startIndex;
                endIndex = median > endIndex ? median : endIndex;
            }
            if (num <= array[median]) binarySearch(low, median - 1, num, array);
            if (num >= array[median]) binarySearch(median + 1, high, num, array);
        }
    }
}
