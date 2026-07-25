package com.shoppingService.streams.easy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Problem2EmployeeWithMaxSalaryInEachDepartment {
	public static void main(String[] args) {
		record Employee(String name, String dept, int salary) {
		}

		List<Employee> employees = List.of(new Employee("Alice", "IT", 80000), new Employee("Bob", "HR", 60000),
				new Employee("Charlie", "IT", 95000), new Employee("David", "HR", 70000),
				new Employee("Eve", "Finance", 75000));
		System.out.println(employees);
		Map<String, Optional<Employee>> map = employees.stream().collect(
				Collectors.groupingBy(Employee::dept, Collectors.maxBy(Comparator.comparing(Employee::salary))));
		System.out.println(map);
	}
}
