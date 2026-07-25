package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindTheSmallestStudentByMarks {
	@Getter
	@AllArgsConstructor
	private static class Student {
		private String name;
		private Integer marks;

		public String toString() {
			return this.name + "(" + this.marks + ")";
		}
	}

	public static void main(String[] args) {
		List<Student> list = Arrays.asList(new Student("A", 69), new Student("B", 94), new Student("C", 61),
				new Student("D", 25));
		System.out.println(list);
		list.stream().min(Comparator.comparing(Student::getMarks)).ifPresentOrElse(System.out::println, null);
	}
}
