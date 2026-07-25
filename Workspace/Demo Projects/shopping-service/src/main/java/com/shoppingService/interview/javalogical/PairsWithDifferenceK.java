package com.shoppingService.interview.javalogical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairsWithDifferenceK {

	private static final Logger log = LoggerFactory.getLogger(PairsWithDifferenceK.class);

	public int countKDifference(int[] nums, int k) {
		int totalPairs = 0;
		for (int index = 0; index < nums.length; index++) {
			int num1 = nums[index];
			for (int subIndex = ++index; subIndex < nums.length; subIndex++) {
				int num2 = nums[subIndex];
				if (num1 - num2 == -k) {
					++totalPairs;
				}
			}
		}
		return totalPairs;
	}

	public static void main(String[] args) {
		int[] numbers = new int[] { 1, 2, 2, 1 };
		int mod = 1;
		PairsWithDifferenceK solution = new PairsWithDifferenceK();
		log.info("Numbers: {}", numbers);
		log.info("K: {}", mod);
		int totalPairs = solution.countKDifference(numbers, mod);
		log.info("Total Pairs: {}", totalPairs);
	}
}
