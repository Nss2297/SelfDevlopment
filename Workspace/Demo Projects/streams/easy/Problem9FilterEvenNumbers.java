package com.shoppingService.streams.easy;

import java.util.List;

public class Problem9FilterEvenNumbers {
	public static void main(String[] args) {
		List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
		System.out.println(nums);
		List<Integer> listOfEvenNums = nums.stream().filter(num -> num % 2 == 0).map(Integer::intValue).toList();
		System.out.println(listOfEvenNums);
	}
}
