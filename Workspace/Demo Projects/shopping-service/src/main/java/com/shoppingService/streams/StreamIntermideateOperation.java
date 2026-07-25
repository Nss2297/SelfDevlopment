package com.shoppingService.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamIntermideateOperation {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	private static class Employee {
		private String name;
		private String department;
		private Integer salary;
	}

	public static void main(String[] args) {
		List<Employee> employees = List.of(new Employee("Muna", "IT", 7500), new Employee("Runa", "HR", 90000),
				new Employee("Kuna", "Finance", 60000), new Employee("Miku", "IT", 75000),
				new Employee("Sonu", "IT", 24000), new Employee("mark", "HR", 65000));
		// Employess with name starting with M
		List<Employee> emps = employees.stream().filter(emp -> emp.getName().startsWith("M"))
				.collect(Collectors.toList());
		log.info("Employee names starting with 'M'");
		emps.stream().forEach(emp -> log.info("{}", emp.getName()));
		log.info("Employees");
		emps.forEach(emp -> log.info("{}", emp.getName()));
		// Employees names to uppercase
		List<String> emp2 = employees.stream().map(emp -> emp.getName().toUpperCase()).collect(Collectors.toList());
		log.info("Uppercase names: {}", Arrays.toString(emp2.toArray()));
		// find distinct words
		List<String> list = List.of("hello", "hello", "world", "world", "java", "stream", "example");
		log.info("Orignal List: {}", Arrays.toString(list.toArray()));
		log.info("Disting: {}", Arrays.toString(list.stream().distinct().toArray()));
		// sort list
		log.info("Sorted list in reversed order: {}", Arrays.toString(list.stream().sorted(Comparator.reverseOrder()).toArray()));
		// skip
		log.info("Skip: {}", Arrays.toString(list.stream().skip(2).toArray()));
		// limit
		log.info("limit: {}", Arrays.toString(list.stream().distinct().limit(3).toArray()));
	}
}
