package com.shoppingService.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitionNumbetsAsOddEven {
	public static void main(String[] args) {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6);
		Map<Boolean, List<Integer>> map = list.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
		System.out.println("=========");
		map.forEach((numType, nums) -> {
			String type = numType.equals(Boolean.TRUE) ? "Even" : "Odd";
			System.out.println("" + type);
			nums.stream().forEach(System.out::println);
			System.out.println("=========");
		});
	}
}
