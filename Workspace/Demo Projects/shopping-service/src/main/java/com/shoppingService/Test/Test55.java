package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test55 {
	private static final Logger log = LoggerFactory.getLogger(Test55.class);

	public static void main(String[] args) {
		Test55 test55 = new Test55();
		int inputArray[] = { 2, 7, 11, 15 };
		int target = 9;
		int indices[] = test55.twoSum(inputArray, target);
		log.info("{}", indices);
	}

	private int[] twoSum(int inputArray[], int target) {
		int indices[] = {};
		for (int a = 0; a < inputArray.length - 1; a++) {
			for (int s = a + 1; s < inputArray.length; s++) {
				if (inputArray[a] + inputArray[s] == target) {
					indices = new int[] { a, s };
				}
			}
		}
		return indices;
	}

}
