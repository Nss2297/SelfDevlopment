package com.shoppingService.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class Test98 {
	@Getter
	@AllArgsConstructor
	@ToString
	private static class Employee {
		private Integer id;
		private String name;
		private Integer category;
	}

	public static void main(String[] args) {
		List<Employee> list1 = List.of(new Employee(1, "Alice", null), new Employee(2, "Bob", 1),
				new Employee(3, "Charlie", 1), new Employee(4, "Diana", 2));
		list1.forEach(System.out::println);
		Map<Integer, List<String>> map = list1.stream().filter(emp -> null != emp.getCategory())
				.collect(Collectors.groupingBy(Employee::getCategory, Collectors.collectingAndThen(Collectors.toList(),
						emps -> emps.stream().map(emp -> emp.getName()).collect(Collectors.toList()))));
		System.out.println(map);
	}
}
