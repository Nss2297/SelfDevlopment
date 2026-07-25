package com.shoppingService.streams.easy;

import java.util.List;

public class Problem14FlattenListOfLists {
	public static void main(String[] args) {
		List<List<Integer>> list = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
		list.stream().flatMap(List::stream).forEach(System.out::println);
	}
}
