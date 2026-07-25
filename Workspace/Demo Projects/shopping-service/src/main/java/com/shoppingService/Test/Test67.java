package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;

public class Test67 {
	public static void main(String[] args) {
		List<String> list1 = List.of("apple", "banana", "mango");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		list1.stream().findFirst().ifPresentOrElse(System.out::println, null);
	}
}
