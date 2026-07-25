package com.shoppingService.streams.easy;

import java.util.Comparator;
import java.util.List;

public class Problem19GetThe2HighestNumber {
	public static void main(String[] args) {

		List<Integer> nums = List.of(10, 20, 5, 15, 30);
		System.out.println(nums);
		nums.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst()
				.ifPresentOrElse(System.out::println, null);
	}
}
