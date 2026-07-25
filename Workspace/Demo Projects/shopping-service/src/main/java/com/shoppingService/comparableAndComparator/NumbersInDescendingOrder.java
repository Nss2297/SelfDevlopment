package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NumbersInDescendingOrder {
public static void main(String[] args) {
	List<Integer> numbers = Arrays.asList(5, 1, 9, 3, 2);
	System.out.println(numbers);
	numbers.sort(Comparator.reverseOrder());
	System.out.println(numbers);
}
}
