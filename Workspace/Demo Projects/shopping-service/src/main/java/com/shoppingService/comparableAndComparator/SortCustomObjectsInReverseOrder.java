package com.shoppingService.comparableAndComparator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortCustomObjectsInReverseOrder {
	public static void main(String[] args) {
		List<Product> list = Arrays.asList(new Product("A", BigDecimal.ONE), new Product("B", BigDecimal.TEN),
				new Product("C", BigDecimal.ZERO));
		System.out.println(list);
		list.sort(Comparator.comparing(Product::getPrice).reversed());
		System.out.println(list);
	}
}
