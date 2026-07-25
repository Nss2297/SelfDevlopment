package com.shoppingService.streams.easy;

import java.util.List;

public class Problem15FindStringsStartingWithJ {
	public static void main(String[] args) {
		List<String> names = List.of("John", "Alice", "Jack", "Bob", "Jason");
		System.out.println(names);
		names.stream().filter(str -> str.startsWith("J")).toList().forEach(System.out::println);
	}
}
