package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortStringsByLengthThenAlphabetically {
	public static void main(String[] args) {
		List<String> words = Arrays.asList("apple", "bat", "banana", "kiwi", "grape");
		System.out.println(words);
		words.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
		System.out.println(words);
	}
}
