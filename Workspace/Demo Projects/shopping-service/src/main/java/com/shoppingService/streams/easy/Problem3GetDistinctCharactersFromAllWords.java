package com.shoppingService.streams.easy;

import java.util.List;
import java.util.stream.Collectors;

public class Problem3GetDistinctCharactersFromAllWords {
	public static void main(String[] args) {
		List<String> words = List.of("hello", "world");
		System.out.println(words);
		List<String> chars = words.stream().flatMap(word -> word.chars().mapToObj(c -> String.valueOf((char) c)))
				.distinct().collect(Collectors.toList());
		System.out.println(chars);
	}
}
