package com.shoppingService.comparableAndComparator.linkedHashMap;

import java.util.LinkedHashMap;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortingByKey {
	@Getter
	@AllArgsConstructor
	private static class Student implements Comparable<Student> {
		private String name;

		public String toString() {
			return this.name;
		}

		@Override
		public int compareTo(Student student) {
			return this.name.compareTo(student.getName());
		}
	}

	public static void main(String[] args) {
		LinkedHashMap<Student, Integer> map = new LinkedHashMap<>();
		map.put(new Student("Bina"), 200);
		map.put(new Student("Akshay"), 400);
		map.put(new Student("Chintu"), 500);
		System.out.println("Before sorting:: " + map);
		TreeMap<Student, Integer> treeMap = new TreeMap<>(map);
		System.out.println("After sorting:: " + treeMap);
	}
}
