package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test73 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(2, 3, 4);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2 = list1.stream().map(num -> num * num).collect(Collectors.toList());
		System.out.println("Square: " + Arrays.toString(list2.toArray()));
	}
}
