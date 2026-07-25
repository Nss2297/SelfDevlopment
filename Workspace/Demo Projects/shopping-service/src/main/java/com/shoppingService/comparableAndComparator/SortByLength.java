package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortByLength {
	public static void main(String[] args) {
		List<String> words = Arrays.asList("Java", "Python", "C", "Go", "Kotlin");
		System.out.println(words);
		words.sort(Comparator.comparingInt(String::length));
		System.out.println(words);
	}
}
