package com.shoppingService.LeetCode.Arrays.Problem_1;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinalSolution {
    private static final Logger log = LoggerFactory.getLogger(FinalSolution.class);

    public static void main(String[] args) {
        FinalSolution test = new FinalSolution();
        int[][] nums = {{2, 7, 11, 15}, {3, 2, 4}, {3, 3}};
        int[] target = new int[]{9, 6, 6};
        for (int a = 0; a < target.length; a++) {
            int[] indices = test.fetchIndices(nums[a], target[a]);
            log.info("{}", indices);
        }
    }

    private int[] fetchIndices(int[] nums, int target) {
        int[] indices = new int[2];
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int a = 0; a < nums.length; a++) {
            int complement = target - nums[a];
            if (hashMap.containsKey(complement)) {
                indices[0] = hashMap.get(complement);
                indices[1] = a;
                break;
            } else {
                hashMap.put(nums[a], a);
            }
        }
        return indices;
    }
}
