package com.shoppingService.Test;

import java.util.List;
import java.util.stream.Collectors;

public class Test90 {

	public static void main(String[] args) {
		List<List<String>> list1 = List.of(List.of("a", "b"), List.of("c", "d", "e"), List.of("f"));
		System.out.println("List: " + list1);
		List<String> list2 = list1.stream().flatMap(List::stream).collect(Collectors.toList());
		System.out.println("" + list2);
	}
}
