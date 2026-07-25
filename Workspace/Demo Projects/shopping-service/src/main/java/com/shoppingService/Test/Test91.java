package com.shoppingService.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test91 {

	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5, 1, 2, 6, 7, 3);
		System.out.println("List: " + list1);
		Map<Boolean, List<Integer>> map = list1.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
		System.out.println(map);
	}
}
