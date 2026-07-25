package com.shoppingService.streams.easy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem5FindAverageSalaryByDepartment {
	private static record Employee(String dept, Integer salary) {
	};

	public static void main(String[] args) {
		List<Employee> emps = List.of(new Employee("A", 1000), new Employee("B", 3000), new Employee("A", 2000),
				new Employee("A", 8000), new Employee("B", 4000));
		System.out.println(emps);
		Map<String, Double> map = emps.stream()
				.collect(Collectors.groupingBy(Employee::dept, Collectors.averagingDouble(Employee::salary)));
		System.out.println(map);
	}
}
