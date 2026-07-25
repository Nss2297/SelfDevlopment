package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test80 {
	public static void main(String[] args) {
		List<String> list1 = List.of("apple", "banana", "apple", "orange", "banana", "apple");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		Map<String, Long> map = list1.stream().collect(Collectors.groupingBy(element -> element, counting()));
		System.out.println(map);
	}
}
