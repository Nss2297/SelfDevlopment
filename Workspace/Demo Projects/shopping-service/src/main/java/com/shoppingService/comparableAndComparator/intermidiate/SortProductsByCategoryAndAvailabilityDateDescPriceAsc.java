package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortProductsByCategoryAndAvailabilityDateDescPriceAsc {
	@Getter
	@AllArgsConstructor
	private static class Product {
		private String name;
		private String category;
		private Integer price;
		private LocalDate date;

		public String toString() {
			return this.name + "-" + this.category + "-" + this.date + "(" + this.price + ")";
		}
	}

	public static void main(String[] args) {
		List<Product> list = Arrays.asList(new Product("Phone", "Electronics", 40000, null),
				new Product("Laptop", "Electronics", 80000, LocalDate.of(2025, 1, 10)),
				new Product("Table", "Furniture", 3000, LocalDate.of(2024, 12, 5)),
				new Product("Chair", "Furniture", 2000, null));
		System.out.println(list);
		list.stream()
				.sorted(Comparator.comparing(Product::getCategory)
						.thenComparing(Product::getDate, Comparator.nullsLast(Comparator.reverseOrder()))
						.thenComparingInt(Product::getPrice))
				.forEach(System.out::println);
//		System.out.println(list);
	}
}
