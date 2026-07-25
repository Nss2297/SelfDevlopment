package com.shoppingService.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test79 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(15, 3, 27, 12, 99, 54, 33);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
		System.out.println("List2: " + list2);
	}
}
