package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test71 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 2, 3, 4, 4, 5);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().distinct().collect(Collectors.toList());
		System.out.println("Disticnt list: " + Arrays.toString(list2.toArray()));
	}
}
