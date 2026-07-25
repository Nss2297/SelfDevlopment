package com.shoppingService.streams.easy;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Problem6FindSecondHighestSalary {
	private static record Employee(String name, BigDecimal salary) {
	}

	public static void main(String[] args) {
		List<Employee> emps = List.of(new Employee("A", BigDecimal.valueOf(1000)),
				new Employee("B", BigDecimal.valueOf(2000)), new Employee("A", BigDecimal.valueOf(3000)),
				new Employee("A", BigDecimal.valueOf(4000)), new Employee("B", BigDecimal.valueOf(5000)));
		System.out.println(emps);
		emps.stream().map(Employee::salary).sorted(Comparator.reverseOrder()).skip(1).findFirst()
				.ifPresentOrElse(System.out::println, null);
	}
}
