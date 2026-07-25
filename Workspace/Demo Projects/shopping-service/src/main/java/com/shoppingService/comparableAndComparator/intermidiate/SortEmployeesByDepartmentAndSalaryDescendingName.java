package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortEmployeesByDepartmentAndSalaryDescendingName {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private String dept;
		private Integer salary;

		public String toString() {
			return this.name + "-" + this.dept + "-" + this.salary;
		}
	}

	public static void main(String[] args) {
		List<Employee> employees = Arrays.asList(new Employee("A", "IT", 70000), new Employee("Amit", "Finance", 60000),
				new Employee("Ravi", "IT", 90000), new Employee("B", "Finance", 80000), new Employee("C", "IT", 70000));
		System.out.println(employees);
		employees.sort(Comparator.comparing(Employee::getDept)
				.thenComparing(Comparator.comparingInt(Employee::getSalary).reversed())
				.thenComparing(Employee::getName));
		System.out.println(employees);
	}
}
