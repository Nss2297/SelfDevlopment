package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test97 {

	public static void main(String[] args) {
		String paragraph = "apple banana apple orange banana banana orange";
		List<String> list1 = List.of(paragraph.split(" "));
		System.out.println(list1);
		list1.stream().collect(Collectors.groupingBy(element -> element, counting())).entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(2).skip(1).findFirst()
				.ifPresentOrElse(System.out::println, null);
	}
}
