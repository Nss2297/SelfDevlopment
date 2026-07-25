package com.shoppingService.LeetCode.Arrays.Problem_53;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KadaneAlgorithm_2 {
    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int a = 0; a < nums.length; a++) {
            sum += nums[a];
            max = max < sum ? sum : max;
            sum = sum < 0 ? 0 : sum;
        }
        return max;
    }

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        list.add(new int[]{1});
        list.add(new int[]{5, 4, -1, 7, 8});
        for (int[] nums : list) {
            System.out.println("Array: " + Arrays.toString(nums) + " sum= " + maxSubArray(nums));
        }
    }
}
