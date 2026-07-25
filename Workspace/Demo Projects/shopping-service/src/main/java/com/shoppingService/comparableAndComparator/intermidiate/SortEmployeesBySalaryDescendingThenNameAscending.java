package com.shoppingService.comparableAndComparator.intermidiate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortEmployeesBySalaryDescendingThenNameAscending {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private Integer age;
		private BigDecimal salary;

		public String toString() {
			return this.name + ":" + this.age + ":" + this.salary;
		}
	}

	public static void main(String[] args) {
		List<Employee> employees = Arrays.asList(new Employee("Alice", 30, BigDecimal.valueOf(50000)),
				new Employee("Bob", 25, BigDecimal.valueOf(70000)),
				new Employee("Charlie", 28, BigDecimal.valueOf(70000)),
				new Employee("David", 35, BigDecimal.valueOf(40000)));
		System.out.println(employees);
		employees.sort(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getName));
		System.out.println(employees);
	}
}
