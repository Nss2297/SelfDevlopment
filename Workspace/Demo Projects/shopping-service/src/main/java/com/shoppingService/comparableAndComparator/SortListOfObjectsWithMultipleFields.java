package com.shoppingService.comparableAndComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class SortListOfObjectsWithMultipleFields {
	@Getter
	@AllArgsConstructor
	private static class Employee {
		private String dept;
		private Integer salary;
		
		public String toString() {
			return dept+":"+salary.toString();
		}
	}

	public static void main(String[] args) {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("Emp1", 1002));
		list.add(new Employee("Emp2", 1001));
		System.out.println("Before sorting: "+list.toString());
		Collections.sort(list, Comparator.comparing(Employee::getDept).thenComparing(Employee::getSalary));
		System.out.println("After sorting: "+list);
	}
}
