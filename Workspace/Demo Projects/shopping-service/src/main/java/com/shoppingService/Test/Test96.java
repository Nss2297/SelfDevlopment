package com.shoppingService.Test;

import java.util.List;
import java.util.stream.Collectors;

public class Test96 {

	public static void main(String[] args) {
		List<String> list1 = List.of("a", "b", "c");
		System.out.println(list1);
		String concatenatedString = list1.stream().collect(Collectors.joining("|", "[", "]"));
		System.out.println(concatenatedString);
	}
}
