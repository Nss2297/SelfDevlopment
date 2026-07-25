package com.shoppingService.comparableAndComparator.linkedList;

import java.util.Collections;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class DefaultSorting {
	@AllArgsConstructor
	@Getter
	@ToString
	private static class Student implements Comparable<Student> {
		private String name;
		private Integer id;
		private Integer rank;

		@Override
		public int compareTo(Student student) {
			if (this.rank > student.getRank()) {
				return 1;
			} else if (this.rank < student.getRank()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public static void main(String[] args) {
		LinkedList<Student> linkedList = new LinkedList<>();
		linkedList.add(new Student("Meet", 32, 2));
		linkedList.add(new Student("Jhon", 11, 5));
		linkedList.add(new Student("Sham", 92, 1));
		linkedList.add(new Student("William", 86, 3));
		linkedList.add(new Student("Harry", 35, 4));
		System.out.println("UnSorted List");
		for (Student student : linkedList) {
			System.out.println(student.toString());
		}
		Collections.sort(linkedList);
		System.out.println("Sorted List");
		for (Student student : linkedList) {
			System.out.println(student.toString());
		}
	}
}
