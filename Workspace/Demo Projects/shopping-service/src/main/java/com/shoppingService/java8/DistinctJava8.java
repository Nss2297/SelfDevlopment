package com.shoppingService.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bouncycastle.util.Integers;

public class DistinctJava8 {

	public static void main(String args[]) {
		List<Integer> list = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5, 5, 5, 55, 5);
		List<Integer> distinctList = list.stream().distinct().collect(Collectors.toList());
		distinctList.stream().forEach(element -> System.out.println(element));
		int total = distinctList.stream().reduce(0, (element1, element2) -> element1 + element2);
		System.out.println("-----------------");
		System.out.println(total);
	}
}
