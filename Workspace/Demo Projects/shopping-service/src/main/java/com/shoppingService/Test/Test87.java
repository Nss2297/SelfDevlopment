package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test87 {

	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 5);
		System.out.println("List: " + list1);
		List<Integer> list2 = list1.stream().collect(Collectors.groupingBy(num -> num, counting())).entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		System.out.println(list2);
	}
}
