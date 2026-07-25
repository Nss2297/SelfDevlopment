package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;

public class Test74 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(10, 25, 3, 7);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		list1.stream().max(Integer::compare).ifPresentOrElse(System.out::println, null);
	}
}
