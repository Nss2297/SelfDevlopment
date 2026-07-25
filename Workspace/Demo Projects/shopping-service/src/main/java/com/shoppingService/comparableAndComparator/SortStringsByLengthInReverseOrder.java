package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortStringsByLengthInReverseOrder {
	public static void main(String[] args) {
		List<String> words = Arrays.asList("Java", "SpringBoot", "C", "Kotlin");
		System.out.println(words);
		words.sort(Comparator.reverseOrder());
		System.out.println(words);
	}

}
