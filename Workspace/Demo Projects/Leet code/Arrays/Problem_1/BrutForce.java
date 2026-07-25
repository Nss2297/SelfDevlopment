package com.shoppingService.LeetCode.Arrays.Problem_1;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrutForce {
    private static final Logger log = LoggerFactory.getLogger(BrutForce.class);

    public static void main(String[] args) {
        BrutForce test = new BrutForce();
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
//		int[] nums = new int[] { 3,2,4};
//		int target = 6;
//		int[] nums = new int[] {3,3};
//		int target = 6;
        int[] indices = test.fetchIndices(nums, target);
        log.info("{}", indices);
    }

    private int[] fetchIndices(int[] nums, int target) {
        int[] indices = new int[2];
        Hashtable<Integer, Integer> hashTable = new Hashtable<>();
        for (int a = 0; a < nums.length; a++) {
            int complement = target - nums[a];
            if (hashTable.containsKey(complement)) {
                indices[0] = hashTable.get(complement);
                indices[1] = a;
                break;
            } else {
                hashTable.put(nums[a], a);
            }
        }
        return indices;
    }
}
