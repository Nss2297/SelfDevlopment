package com.shoppingService.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test77 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 2, 3, 5, 6, 1);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		Set<Integer> set = new HashSet<>();
		List<Integer> list2 = list1.stream().filter(element -> !set.add(element)).map(element -> element)
				.collect(Collectors.toList());
		System.out.println(list2);
	}
}
