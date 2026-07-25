package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test78 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 2, 3, 5, 6, 1);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().collect(Collectors.groupingBy(element -> element, counting())).entrySet()
				.stream().filter(element -> element.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
		System.out.println(list2);
	}
}
