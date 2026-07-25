package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Find2ScorersUsingStream {
	public static void main(String[] args) {
		List<Student> list = Arrays.asList(new Student("A", 45), new Student("B", 78), new Student("C", 28),
				new Student("D", 81), new Student("E", 36));
		System.out.println(list);
		list.stream().sorted(Comparator.comparing(Student::getMarks).reversed()).limit(2).forEach(System.out::println);
	}
}
