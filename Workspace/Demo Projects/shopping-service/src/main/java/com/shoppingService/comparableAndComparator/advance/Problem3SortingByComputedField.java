package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem3SortingByComputedField {
	@Getter
	@AllArgsConstructor
	private static class Student {
		private String name;
		private Integer marks1, marks2, marks3;

		public String toString() {
			return this.name + "(" + this.marks1 + ", " + this.marks2 + ", " + this.marks3 + ")";
		}
	}

	public static void main(String[] args) {
		List<Student> students = Arrays.asList(new Student("Alice", 90, 85, 80), new Student("Bob", 70, 95, 85),
				new Student("Charlie", 90, 85, 80));
		System.out.println(students);
		students.sort(
				Comparator.comparing((Student s) -> s.getMarks1() * 0.5 + s.getMarks2() * 0.3 + s.getMarks3() * 0.2,
						Comparator.reverseOrder()).thenComparing(Student::getName, Comparator.naturalOrder()));
//		86.5,80.5,86.5
		System.out.println(students);
	}
}
