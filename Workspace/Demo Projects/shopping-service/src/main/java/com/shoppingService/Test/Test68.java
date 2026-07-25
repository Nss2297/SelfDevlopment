package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test68 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().filter(num -> num % 2 == 0).collect(Collectors.toList());
		System.out.println("List2: " + Arrays.toString(list2.toArray()));
	}
}
