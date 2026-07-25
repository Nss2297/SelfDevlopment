package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortNestedObjectsCustomerByCityThenByEachOrderCount {
	@Getter
	@AllArgsConstructor
	private static class Customer {
		private String name;
		private String city;
		private List<String> orders;

		public String toString() {
			return this.name + "(" + this.city + ")" + this.orders;
		}
	}

	public static void main(String[] args) {
		List<Customer> list = Arrays.asList(new Customer("A", "Mumbai", Arrays.asList("O1", "O2")),
				new Customer("B", "Pune", Arrays.asList("O3")),
				new Customer("B", "Goa", Arrays.asList("O3", "02")),
				new Customer("As", "Mumbai", Arrays.asList("O4", "O5", "O6")));
		System.out.println(list);
		list.sort(Comparator.comparing(Customer::getCity).thenComparing(c -> c.getOrders().size()));
		System.out.println(list);
	}
}
