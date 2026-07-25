package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortNullValuesSafely {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("A", "B", null, "C");
		System.out.println(list);
		list.sort(Comparator.nullsLast(Comparator.naturalOrder()));
		System.out.println(list);
	}
}
