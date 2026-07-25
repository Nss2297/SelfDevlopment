package com.shoppingService.comparableAndComparator.hashSet;

import java.util.HashSet;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AscendingOrder {
	@Getter
	@AllArgsConstructor
	private static class Student implements Comparable<Student> {
		Integer marks;

		public String toString() {
			return this.marks.toString();
		}

		@Override
		public int compareTo(Student student) {
			return this.marks.compareTo(student.marks);
		}
	}

	public static void main(String[] args) {
		// New HashSet
		HashSet<Student> set = new HashSet<>();
		// Adding elements to the set
		set.add(new Student(500));
		set.add(new Student(300));
		set.add(new Student(400));
		set.add(new Student(100));
		set.add(new Student(200));
		// Print Before sort
		System.out.println("Before sort elements in ascending order : " + set);
		TreeSet<Student> treeSet = new TreeSet<Student>(set);
		// Print after sorting
		System.out.println("After sort elements in ascending order : " + treeSet);
	}
}
