package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test70 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(5, 3, 8, 1);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().sorted().collect(Collectors.toList());
		System.out.println("Sorted elements: " + Arrays.toString(list2.toArray()));
	}
}
