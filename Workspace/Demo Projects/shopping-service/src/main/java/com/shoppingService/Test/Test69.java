package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;

public class Test69 {
	public static void main(String[] args) {
		List<String> list1 = List.of("a", "b", "c", "d");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		long count = list1.stream().count();
		System.out.println("Number of elements: " + count);
	}
}
