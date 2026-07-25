package com.shoppingService.streams.easy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem18ConvertListOfNumbersToCommaSeparatedString {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		System.out.println(list);
		String joinnedList = list.stream().map(String::valueOf).collect(Collectors.joining(","));
		System.out.println(joinnedList);
	}
}
