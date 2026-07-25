package com.shoppingService.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Group employees by department
public class Test65 {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private String dept;
		private Integer salary;
	}

	public static void main(String[] args) {
		List<Employee> emps = List.of(new Employee("John Doe", "IT", 75000), new Employee("Jane Smith", "HR", 60000),
				new Employee("John Doe", "Finance", 80000), new Employee("Jane Smith", "IT", 70000),
				new Employee("John Doe", "HR", 65000));
		Map<String, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(emp -> emp.getDept()));
		map.forEach((dept, empList) -> {
			System.out.println("" + dept);
			System.out.println("=============");
			empList.stream().forEach(emp -> {
				System.out.println(emp.getName());
			});
		});
		System.out.println("Map2");
		Map<String, List<String>> map2 = emps.stream().collect(Collectors.groupingBy(emp -> emp.getDept(),
				Collectors.mapping(emp -> emp.getName(), Collectors.toList())));
		map2.forEach((dept, empList) -> {
			System.out.println("" + dept);
			System.out.println("=============");
			empList.stream().forEach(System.out::println);
		});
	}
}
