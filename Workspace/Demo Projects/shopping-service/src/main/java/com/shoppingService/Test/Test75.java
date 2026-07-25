package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;

public class Test75 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		int sum = list1.stream().mapToInt(Integer::intValue).sum();
		System.out.println("Sum: " + sum);
	}
}
