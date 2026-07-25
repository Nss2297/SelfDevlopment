package com.shoppingService.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamGrouping {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	private static class Employee {
		Long employeeId;
		String department;
	}

	private static Stream<Map.Entry<String, Employee>> convertMapIntoStream(Set<Map.Entry<String, Employee>> set) {
		return set.stream();
	}

	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee(1L, "Dept1");
		Employee employee2 = new Employee(2L, "Dept2");
		Employee employee3 = new Employee(3L, "Dept1");
		Employee employee4 = new Employee(4L, "Dept2");
		Employee employee5 = new Employee(5L, "Dept2");
		Employee employee6 = new Employee(6L, "Dept1");
		employees.add(employee1);
		employees.add(employee2);
		employees.add(employee3);
		employees.add(employee4);
		employees.add(employee5);
		employees.add(employee6);
		// Group employees by department
		Map<String, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
//		Stream stream = convertMapIntoStream(map.entrySet());
		log.info("{}", map);
	}
}
