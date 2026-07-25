package com.shoppingService.streams.easy;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem22CalculateFrequencyOfEachCharacter {
	public static void main(String[] args) {
		String word = "banana";
		System.out.println(word);
		IntStream chars = word.chars();
		Map<Character, Long> map = chars.mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
		System.out.println(map);
	}
}
