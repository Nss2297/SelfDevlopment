package com.shoppingService.streams.easy;

import java.util.List;

public class Problem10CountElementsGreaterthan10 {
	public static void main(String[] args) {
		List<Integer> nums = List.of(5, 12, 18, 7, 10, 22);
		System.out.println(nums);
		long count = nums.stream().filter(num -> num > 10).count();
		System.out.println(count);
	}
}
