package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortEmployeesByNameLength {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private Integer id;

		public String toString() {
			return this.name + "(" + this.id.toString() + ")";
		}
	}

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("AAA", 1), new Employee("A", 2), new Employee("aaaa", 3),
				new Employee("aa", 4));
		System.out.println(list);
		list.sort(Comparator.comparingInt(emp -> emp.getName().length()));
		System.out.println(list);
	}
}
