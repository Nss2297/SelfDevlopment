package com.shoppingService.streams.easy;

import java.util.List;

public class Problem17FindAverageLengthOfWords {
	public static void main(String[] args) {
		List<String> words = List.of("cat", "elephant", "dog", "hippo");
		System.out.println(words);
		words.stream().distinct().mapToInt(String::length).average().ifPresentOrElse(System.out::println, null);
	}
}
