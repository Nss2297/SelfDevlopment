package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortProductsByCategoryPriceAndAvailability {
	@Getter
	@AllArgsConstructor
	private static class Product {
		private String category;
		private Integer price;
		private LocalDate availableFrom;

		public String toString() {
			return this.category + "(" + this.price + ")" + this.availableFrom;
		}
	}

	public static void main(String[] args) {
		List<Product> list = Arrays.asList(new Product("Laptop", 90000, LocalDate.of(2025, 1, 10)),
				new Product("Laptop", 80000, null), new Product("Phone", 40000, LocalDate.of(2024, 12, 5)),
				new Product("Phone", 50000, null), new Product("Laptop", 80000, LocalDate.of(2025, 1, 10)),
				new Product("Phone", 50000, LocalDate.of(2025, 1, 10)));
		System.out.println(list);
		list.sort(Comparator.comparing(Product::getCategory).thenComparing(Product::getPrice).thenComparing(
				Comparator.comparing(Product::getAvailableFrom, Comparator.nullsLast(Comparator.naturalOrder()))));
		System.out.println(list);
	}
}
