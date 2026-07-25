package com.shoppingService.streams.easy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Problem21FindFirstNonEmptyString {
	public static void main(String[] args) {
		List<String> words = List.of("", " ", "hello", "world");
		System.out.println(words);
		words.stream().filter(StringUtils::isNotBlank).findFirst().ifPresentOrElse(System.out::println, null);
	}
}
