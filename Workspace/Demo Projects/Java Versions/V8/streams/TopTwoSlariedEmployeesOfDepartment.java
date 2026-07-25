package com.shoppingService.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TopTwoSlariedEmployeesOfDepartment {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		String name;
		String dept;
		Integer salary;
	}

	public static void main(String[] args) {
		List<Employee> employees = List.of(new Employee("Alice", "HR", 5000), new Employee("Bob", "HR", 7000),
				new Employee("Charlie", "HR", 6000), new Employee("David", "IT", 10000),
				new Employee("Eve", "IT", 12000), new Employee("Frank", "IT", 11000));
		Map<String, List<String>> map = employees.stream()
				.collect(Collectors.groupingBy(emp -> emp.getDept(),
						Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream()
										.sorted(Comparator.comparingInt(emp -> ((Employee) emp).getSalary()).reversed())
										.limit(2).map(emp -> emp.getName()).collect(Collectors.toList()))));
		System.out.println(map);
	}
}
