package com.shoppingService.streams;

import java.util.Map;
import java.util.stream.Collectors;

public class OccurenceCharacters {
	public static void main(String[] args) {
		String input = "banana";
		System.out.println("String: " + input);
		Map<Character, Long> map = input.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
		System.out.println(map);
	}
}
