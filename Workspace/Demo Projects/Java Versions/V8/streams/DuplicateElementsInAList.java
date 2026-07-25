package com.shoppingService.streams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DuplicateElementsInAList {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		List<Integer> list = List.of(1, 2, 3, 2, 4, 5, 1, 6);
		Set<Integer> set2 = list.stream().filter(num -> !set.add(num)).collect(Collectors.toSet());
		System.out.println(set2);
	}
}
