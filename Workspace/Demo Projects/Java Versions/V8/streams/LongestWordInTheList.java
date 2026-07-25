package com.shoppingService.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LongestWordInTheList {
	public static void main(String[] args) {
		List<String> list = List.of("Java", "Streams", "Interview", "Questions");
		System.out.println(Arrays.toString(list.toArray()));
		String longestWord = list.stream().max(Comparator.comparingInt(String::length)).orElse("");
		System.out.println(longestWord);
	}
}
