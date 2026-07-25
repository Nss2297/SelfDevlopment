package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Test84 {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private String dept;
		private int salary;
	}

	public static void main(String[] args) {
		List<Employee> list1 = List.of(new Employee("Alice", "IT", 5000), new Employee("Bob", "IT", 7000),
				new Employee("Charlie", "HR", 4000), new Employee("David", "HR", 4500));
		System.out.println("List: " + list1);
		Map<String, Double> map = list1.stream()
				.collect(Collectors.groupingBy(Employee::getDept, Collectors.averagingInt(Employee::getSalary)));
		System.out.println("Flatted List: " + map);
	}
}
