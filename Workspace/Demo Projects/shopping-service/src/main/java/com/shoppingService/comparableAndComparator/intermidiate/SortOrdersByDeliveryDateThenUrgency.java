package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortOrdersByDeliveryDateThenUrgency {
	private enum Urgency {
		LOW, MEDIUM, HIGH
	}

	@Getter
	@AllArgsConstructor
	private static class Order {
		private String id;
		private LocalDate deliveryDate;
		Urgency urgency;

		public String toString() {
			return this.id + "::" + this.deliveryDate + "::" + this.urgency;
		}
	}

	public static void main(String[] args) {
		List<Order> orders = Arrays.asList(new Order("O1", LocalDate.of(2025, 10, 10), Urgency.MEDIUM),
				new Order("O2", LocalDate.of(2025, 10, 8), Urgency.HIGH),
				new Order("O3", LocalDate.of(2025, 10, 8), Urgency.LOW));
		System.out.println(orders);
		orders.sort(Comparator.comparing(Order::getDeliveryDate).reversed()
				.thenComparing(Comparator.comparing(Order::getUrgency).reversed()));
		System.out.println(orders);
	}
}
