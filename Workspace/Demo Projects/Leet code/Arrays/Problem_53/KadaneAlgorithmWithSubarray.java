package com.shoppingService.LeetCode.Arrays.Problem_53;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KadaneAlgorithmWithSubarray {
    private static int startIndex = -1;
    private static int endIndex = -1;

    private static String fetchSubArray(int[] nums) {
        if (startIndex < 0 && endIndex < 0) return Arrays.toString(new int[]{});
        int[] subArray = new int[endIndex - startIndex + 1];
        for (int a = 0; a < subArray.length; a++) {
            subArray[a] = nums[startIndex];
            ++startIndex;
        }
        return Arrays.toString(subArray);
    }

    private static int maxSubArray(int[] nums) {
        startIndex = 0;
        endIndex = -1;
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int a = 0; a < nums.length; a++) {
            sum += nums[a];
            if (sum > max) {
                max = sum;
                endIndex = a;
            }
            if (sum < 0) {
                sum = 0;
                startIndex = a + 1;
                endIndex = -1;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        list.add(new int[]{1});
        list.add(new int[]{5, 4, -1, 7, 8});
        for (int[] nums : list) {
            System.out.println("Array: " + Arrays.toString(nums) + " sum= " + maxSubArray(nums) + " Sub Array: " + fetchSubArray(nums));
        }
    }
}
