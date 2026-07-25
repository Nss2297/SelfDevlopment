package com.shoppingService.Test;

import java.util.Comparator;
import java.util.List;

public class Test94 {

	public static void main(String[] args) {
		List<Integer> list1 = List.of(5000, 7000, 7000, 9000, 10000);
		System.out.println("List: " + list1);
		list1.stream().distinct().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst()
				.ifPresentOrElse(System.out::println, null);
	}
}
