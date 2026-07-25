package com.shoppingService.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SecondHighestNumber {
	public static void main(String[] args) {
		List<Integer> list = List.of(10, 20, 30, 40, 50);
		System.out.println(Arrays.toString(list.toArray()));
		int secondHighestNumber = list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().orElseThrow();
		System.out.println(secondHighestNumber);
	}
}
