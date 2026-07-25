package com.shoppingService.comparableAndComparator.advance;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Problem4SortByNestedObjectFieldsWithNullHandling {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Customer {
		private String name;

		public String toString() {
			return this.name;
		}
	}

	@Getter
	@AllArgsConstructor
	private static class Order {
		private Customer customer;
		private BigDecimal amount;

		public String toString() {
			return this.customer + "(" + this.amount + ")";
		}
	}

	public static void main(String[] args) {
		List<Order> orders = Arrays.asList(new Order(new Customer("Alice"), BigDecimal.valueOf(500.0)),
				new Order(null, BigDecimal.valueOf(700.0)), new Order(new Customer("Bob"), BigDecimal.valueOf(300.0)));
		System.out.println(orders);
		orders.sort(Comparator
				.comparing((Order o) -> Optional.ofNullable(o.getCustomer()).map(Customer::getName).orElse(null),
						Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(Order::getAmount, Comparator.reverseOrder()));
		System.out.println(orders);
	}
}
