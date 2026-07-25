package com.shoppingService.streams.easy;

import java.util.List;

public class Problem8SquareNumbers {
	public static void main(String[] args) {
		List<Integer> nums = List.of(2, 3, 4, 5);
		System.out.println(nums);
		List<Integer> squares = nums.stream().map(num -> num * num).toList();
		System.out.println(squares);
	}
}
