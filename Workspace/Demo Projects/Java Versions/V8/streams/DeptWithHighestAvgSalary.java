package com.shoppingService.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DeptWithHighestAvgSalary {
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
		Map<String, Double> map1 = employees.stream()
				.collect(Collectors.groupingBy(emp -> emp.getDept(), Collectors.averagingInt(emp -> emp.getSalary())));
		System.out.println("Ave salary dept wise: " + map1);
		Map.Entry<String, Double> map2 = map1.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
		System.out.println("Dept with highset avg slary: " + map2);
	}
}
