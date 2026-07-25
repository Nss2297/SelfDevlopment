package com.shoppingService.comparableAndComparator.advance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem6SortEmployeesByDepartmentSalaryDescendingJoinDateNullsLast {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String dept;
		private BigDecimal salary;
		private LocalDate date;

		public String toString() {
			return this.dept + "(" + this.salary + ")" + this.date;
		}
	}

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("IT", BigDecimal.valueOf(90000.0), LocalDate.of(2020, 1, 1)),
				new Employee("Finance", BigDecimal.valueOf(95000.0), null),
				new Employee("IT", BigDecimal.valueOf(95000.0), LocalDate.of(2021, 5, 10)),
				new Employee("Finance", BigDecimal.valueOf(95000.0), LocalDate.of(2019, 3, 15)));
		System.out.println(list);
		list.sort(Comparator.comparing(Employee::getDept).thenComparing(Employee::getSalary, Comparator.reverseOrder())
				.thenComparing(Employee::getDate, Comparator.nullsLast(Comparator.reverseOrder())));
		System.out.println(list);
	}
}
