package com.shoppingService.comparableAndComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByName {
	public static void main(String[] args) {
		List<StudentForSorting> list = new ArrayList(List.of(new StudentForSorting("C", 3L),
				new StudentForSorting("A", 1L), new StudentForSorting("B", 2L)));
		System.out.println(list);
		list.sort(Comparator.comparing(s -> s.name));
		System.out.println(list);
	}
}
