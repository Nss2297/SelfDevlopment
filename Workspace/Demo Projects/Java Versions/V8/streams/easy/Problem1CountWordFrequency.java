package com.shoppingService.streams.easy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problem1CountWordFrequency {
	public static void main(String[] args) {
		List<String> words = List.of("apple", "banana", "apple", "orange", "banana", "apple");
		System.out.println(words);
		Map<String, Long> map = words.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(map);
	}
}
