package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test81 {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		Map<Boolean, List<Integer>> map = list1.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
		System.out.println(map);
	}
}
