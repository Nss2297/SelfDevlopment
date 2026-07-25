package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem1MultiLevelComparatorWithNullsAndMixedData {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private Integer age;
		private Double salary;

		public String toString() {
			return this.name + "-" + this.age + "-" + this.salary;
		}
	}

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("Alice", 30, 70000.0), new Employee("Bob", null, 60000.0),
				new Employee("Charlie", 30, null), new Employee(null, 25, 75000.0), new Employee("David", 25, 70000.0));
		System.out.println(list);
		list.sort(Comparator.comparing((Employee emp) -> emp.getName() == null, Comparator.naturalOrder())
				.thenComparing(Employee::getAge, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(Employee::getSalary, Comparator.nullsLast(Comparator.reverseOrder())));
		System.out.println(list);
	}
}
