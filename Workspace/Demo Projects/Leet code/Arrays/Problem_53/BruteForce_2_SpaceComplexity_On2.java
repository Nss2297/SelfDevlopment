package com.shoppingService.LeetCode.Arrays.Problem_53;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce_2_SpaceComplexity_On2 {
    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int a = 0; a < nums.length; a++) {
            int sum = 0;
            for (int s = a; s < nums.length; s++) {
                sum = sum + nums[s];
                if (max < sum) max = sum;
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
            System.out.println("Array: " + Arrays.toString(nums) + " sum= " + maxSubArray(nums));
        }
    }
}
