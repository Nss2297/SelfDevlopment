package com.shoppingService.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListsIntoList {
	public static void main(String[] args) {
		List<List<Integer>> lists = List.of(List.of(1, 2, 3), List.of(4, 5), List.of(6, 7, 8));
		List<Integer> flatList = lists.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
		System.out.println(Arrays.toString(flatList.toArray()));
	}
}
