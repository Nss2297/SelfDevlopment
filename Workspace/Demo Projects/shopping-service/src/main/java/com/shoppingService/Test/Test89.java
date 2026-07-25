package com.shoppingService.Test;

import java.util.Comparator;
import java.util.List;

public class Test89 {

	public static void main(String[] args) {
		List<String> list1 = List.of("apple", "banana", "strawberry", "kiwi");
		System.out.println("List: " + list1);
		list1.stream().max(Comparator.comparingInt(String::length)).ifPresentOrElse(System.out::println, null);
	}
}
