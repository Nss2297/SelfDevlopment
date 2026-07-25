package com.shoppingService.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test56 {
	private static final Logger log = LoggerFactory.getLogger(Test56.class);

	public static void main(String[] args) {
		Test56 test56 = new Test56();
		int nums[] = new int[] { 1, 1, 2 };
		int uniqueNums = test56.removeDuplicates(nums);
		log.info("{}", uniqueNums);
	}

	private int removeDuplicates(int nums[]) {
		int arrayLength = nums.length;
		Set<Integer> set = new LinkedHashSet<>();
		for (int a = 0; a < arrayLength; a++) {
			int leftNum = nums[a];
			for (int s = 0; s < arrayLength; s++) {
				int rightNum = nums[s];
				if (leftNum != rightNum && !set.contains(leftNum)) {
					set.add(leftNum);
				}
			}
		}
		return set.size();
	}
}
