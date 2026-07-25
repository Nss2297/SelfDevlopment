package com.shoppingService.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test82 {
	public static void main(String[] args) {
		List<String> list1 = List.of("cat", "elephant", "dog", "hippopotamus");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		list1.stream().max(Comparator.comparing(String::length)).ifPresentOrElse(System.out::println, null);
	}
}
