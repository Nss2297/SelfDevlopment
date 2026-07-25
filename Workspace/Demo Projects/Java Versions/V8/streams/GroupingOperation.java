package com.shoppingService.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupingOperation {
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
		Map<String, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(Employee::getDept));
		map.forEach((dept, employees) -> {
			log.info("{}", dept);
			employees.stream().forEach(emp -> {
				log.info("{}", emp.getName());
			});
		});
	}
}
