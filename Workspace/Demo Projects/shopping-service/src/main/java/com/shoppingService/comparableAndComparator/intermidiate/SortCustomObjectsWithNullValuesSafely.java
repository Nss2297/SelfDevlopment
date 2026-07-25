package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortCustomObjectsWithNullValuesSafely {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("John", null, "Alice", "Bob", null);
		System.out.println(names);
		names.sort(Comparator.nullsLast(Comparator.naturalOrder()));
		System.out.println(names);
		names = Arrays.asList("John", null, "Alice", "Bob", null);
		System.out.println(names);
		names.sort(Comparator.nullsLast(Comparator.reverseOrder()));
		System.out.println(names);
	}
}
