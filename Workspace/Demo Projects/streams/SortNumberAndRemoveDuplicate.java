package com.shoppingService.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SortNumberAndRemoveDuplicate {
	public static void main(String[] args) {
		List<Integer> list = List.of(5, 3, 7, 3, 9, 1, 5);
		System.out.println("List : " + list);
		List<Integer> sortedList = list.stream().sorted().distinct().collect(Collectors.toList());
		System.out.println("Sorted list with unique elemtns: " + Arrays.toString(sortedList.toArray()));
		Set<Integer> set = list.stream().sorted().collect(Collectors.toSet());
		System.out.println("Sorted set: " + Arrays.toString(set.toArray()));
	}
}
