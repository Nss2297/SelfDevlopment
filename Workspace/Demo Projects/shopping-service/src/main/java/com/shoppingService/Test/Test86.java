package com.shoppingService.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test86 {

	public static void main(String[] args) {
		List<String> list1 = List.of("Alex", "John", "Christopher", "Bob");
		System.out.println("List: " + list1);
		String joinnedString = list1.stream().sorted(Comparator.comparingInt(String::length))
				.collect(Collectors.joining("-"));
		System.out.println(joinnedString);
	}
}
