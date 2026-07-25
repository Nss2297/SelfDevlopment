package com.shoppingService.streams.easy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem7PartitionNumbersIntoEvenAndOdd {
	public static void main(String[] args) {
		List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
		System.out.println(nums);
		Map<Boolean, List<Integer>> map = nums.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
		System.out.println("Even "+map.get(true));
		System.out.println("Odd "+map.get(false));
	}
}
