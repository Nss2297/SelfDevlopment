package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MultiLevelSort {
	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee(91, "A"), new Employee(12, "B"));
		System.out.println(list);
		list.sort(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName));
		System.out.println(list);
	}
}
