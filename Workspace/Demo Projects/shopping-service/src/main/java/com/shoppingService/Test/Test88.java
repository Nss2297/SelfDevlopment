package com.shoppingService.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Test88 {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Employee {
		private int id;
		private String name;
		private String dept;
		private double salary;
	}

	public static void main(String[] args) {
		List<Employee> list1 = List.of(new Employee(1, "Alice", "IT", 7000), new Employee(2, "Bob", "HR", 6000),
				new Employee(3, "Charlie", "IT", 9000), new Employee(4, "Diana", "HR", 7500));
		System.out.println("List: " + list1);
		Map<String, Integer> map = list1.stream()
				.collect(Collectors.groupingBy(Employee::getDept,
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
								empOpt -> empOpt.get().getId())));
		System.out.println(map);
	}
}
