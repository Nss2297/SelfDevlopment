package com.shoppingService.streams.easy;

import java.util.List;

public class Problem12SumOfAllNumbers {
	public static void main(String[] args) {
		List<Integer> nums = List.of(1, 2, 3, 4, 5);
		System.out.println(nums);
		long sum = nums.stream().mapToInt(Integer::intValue).sum();
		System.out.println(sum);
	}
}
