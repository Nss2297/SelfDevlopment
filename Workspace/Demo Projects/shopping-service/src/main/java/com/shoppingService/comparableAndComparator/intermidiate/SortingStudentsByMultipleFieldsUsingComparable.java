package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortingStudentsByMultipleFieldsUsingComparable {
	@Getter
	@AllArgsConstructor
	private static class Student implements Comparable<Student> {
		private String name;
		private Integer marks;

		public String toString() {
			return this.name + "(" + this.marks + ")";
		}

		@Override
		public int compareTo(Student o) {
			int result = this.marks.compareTo(o.getMarks());
			if (0 == result) {
				result = this.name.compareTo(o.getName());
			}
			return result;
		}
	}

	public static void main(String[] args) {
		List<Student> list = Arrays.asList(new Student("Raj", 85), new Student("Amit", 92), new Student("Neha", 85));
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
		List<Student> list2 = Arrays.asList(new Student("Raj", 85), new Student("Amit", 92), new Student("Neha", 85));
		System.out.println(list2);
		list2.sort(Comparator.comparingInt(Student::getMarks).thenComparing(Student::getName));
		System.out.println(list2);
	}
}
