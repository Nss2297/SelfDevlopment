package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.List;
import java.util.stream.Collectors;

public class Test92 {

	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5, 1, 2, 6, 7, 3);
		System.out.println("List: " + list1);
		List<Integer> list2 = list1.stream().collect(Collectors.groupingBy(Integer::intValue, counting())).entrySet()
				.stream().filter(pair -> pair.getValue() > 1).map(pair -> pair.getKey()).collect(Collectors.toList());
		System.out.println(list2);
	}
}
