package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortEmployeesByDepartmentSalaryDescendingAndNameCaseInsensitive {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private String dept;
		private Integer salary;

		public String toString() {
			return this.name + "(" + this.salary + ")" + dept;
		}
	}

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("John", "HR", 40000), new Employee("Alice", "IT", 70000),
				new Employee("Bob", "IT", 90000), new Employee("Carol", "HR", 50000));
		System.out.println(list);
		list.sort(Comparator.comparing(Employee::getDept).thenComparing(Employee::getSalary, Comparator.reverseOrder())
				.thenComparing(e -> e.getName().toLowerCase()));
		System.out.println(list);
	}
}
