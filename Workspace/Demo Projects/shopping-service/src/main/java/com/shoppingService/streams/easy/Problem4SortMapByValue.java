package com.shoppingService.streams.easy;

import java.util.Map;

public class Problem4SortMapByValue {
	public static void main(String[] args) {
		Map<String, Integer> scores = Map.of("Alice", 90, "Bob", 70, "Charlie", 85);
		System.out.println(scores);
		scores.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.forEach(obj -> System.out.println(obj.getKey() + "=" + obj.getValue()));
	}

}
