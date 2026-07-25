package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortStudentsByMarksDescendingNullLast {
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
		List<Student> students = Arrays.asList(new Student("Ravi", 85), new Student("Neha", null),
				new Student("Amit", 95), new Student("Rita", 70));
		System.out.println(students);
		students.sort(Comparator.comparing(Student::getMarks, Comparator.nullsLast(Comparator.reverseOrder())));
		System.out.println(students);
	}
}
