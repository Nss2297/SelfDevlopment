package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;

public class SortStudentsByRollNo {
	@AllArgsConstructor
	private static class Student implements Comparable<Student> {
		private Integer rollNo;
		private String name;

		public String toString() {
			return this.rollNo.toString() + ":" + this.name;
		}

		@Override
		public int compareTo(Student o) {
			return this.rollNo.compareTo(o.rollNo);
		}
	}

	public static void main(String[] args) {
		List<Student> list = Arrays.asList(new Student(3, "A"), new Student(1, "B"), new Student(2, "C"));
		System.out.println("Before sorting: " + list);
		Collections.sort(list);
		System.out.println("After sorting: " + list);
	}
}
