package com.shoppingService.streams.easy;

import java.util.Comparator;
import java.util.List;

public class Problem23FindLongestStringWithoutUsingLoops {
	public static void main(String[] args) {
		List<String> list = List.of("Java", "SpringBoot", "StreamAPI", "Kotlin");
		System.out.println(list);
		list.stream().sorted(Comparator.comparing(String::length, Comparator.reverseOrder())).limit(1).findFirst()
				.ifPresentOrElse(System.out::println, null);
	}
}
