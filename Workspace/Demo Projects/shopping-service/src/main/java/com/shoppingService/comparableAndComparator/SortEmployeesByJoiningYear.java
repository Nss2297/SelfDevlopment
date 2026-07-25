package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortEmployeesByJoiningYear {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String name;
		private Integer joinYear;

		public String toString() {
			return this.name + "(" + this.joinYear + ")";
		}
	}

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("A", 2022), new Employee("B", 2018), new Employee("C", 2020));
		System.out.println(list);
		list.sort(Comparator.comparingInt(Employee::getJoinYear));
		System.out.println(list);
	}
}
