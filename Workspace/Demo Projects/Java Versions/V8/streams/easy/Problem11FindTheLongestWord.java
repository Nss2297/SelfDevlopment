package com.shoppingService.streams.easy;

import java.util.Comparator;
import java.util.List;

public class Problem11FindTheLongestWord {
	public static void main(String[] args) {
		List<String> words = List.of("cat", "elephant", "dog", "hippopotamus");
		words.stream().max(Comparator.comparing(str -> str.length())).ifPresentOrElse(System.out::println, null);
	}
}
