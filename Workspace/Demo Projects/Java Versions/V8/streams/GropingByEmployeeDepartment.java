package com.shoppingService.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GropingByEmployeeDepartment {

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	private static class Employee {
		Long employeeId;
		String department;
	}

	private static Stream<String> convertMapValuesIntoStream(Map map) {
		return map.values().stream();
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
		Map<String, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
		List<Employee> dept1Emp = map.get("Dept1");
		List<Employee> dept2Emp = map.get("Dept2");
		log.info("Dept1: {}", Arrays.toString(dept1Emp.stream()
				.map(emp -> emp.getEmployeeId() + "=" + emp.getDepartment()).collect(Collectors.toList()).toArray()));
		log.info("Dept2: {}", Arrays.toString(dept2Emp.stream()
				.map(emp -> emp.getEmployeeId() + "=" + emp.getDepartment()).collect(Collectors.toList()).toArray()));
	}
}
