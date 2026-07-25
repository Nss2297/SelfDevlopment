package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortOrdersByDateNewestFirstThenByTotalAmountThenByID {
	@Getter
	@AllArgsConstructor
	private static class Order {
		private Integer orderNo;
		private LocalDate date;
		private Integer amount;

		public String toString() {
			return this.orderNo + "(" + this.date + ")" + this.amount;
		}
	}

	public static void main(String[] args) {
		List<Order> orders = Arrays.asList(new Order(101, LocalDate.of(2023, 6, 15), 5500),
				new Order(102, LocalDate.of(2024, 1, 10), 4000), new Order(103, LocalDate.of(2024, 1, 10), 7000),
				new Order(104, LocalDate.of(2022, 9, 5), 2000));
		System.out.println(orders);
		orders.sort(Comparator.comparing(Order::getDate).reversed()
				.thenComparing(Order::getAmount, Comparator.reverseOrder()).thenComparingInt(Order::getOrderNo));
		System.out.println(orders);
	}
}
