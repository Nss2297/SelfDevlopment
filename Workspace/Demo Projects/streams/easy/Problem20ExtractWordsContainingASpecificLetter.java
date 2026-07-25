package com.shoppingService.streams.easy;

import java.util.List;

public class Problem20ExtractWordsContainingASpecificLetter {
	public static void main(String[] args) {
		List<String> words = List.of("table", "chair", "desk", "lamp", "shelf");
		System.out.println(words);
		List<String> wordsWithChar = words.stream().filter(str -> str.contains("a")).toList();
		System.out.println(wordsWithChar);
	}
}
