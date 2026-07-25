package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortStringsAlphabetically {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("Java", "SpringBoot", "C", "Kotlin");
		System.out.println(list);
		list.sort(Comparator.naturalOrder());
		System.out.println(list);
	}
}
