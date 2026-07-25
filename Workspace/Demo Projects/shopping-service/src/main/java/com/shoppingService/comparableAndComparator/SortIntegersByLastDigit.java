package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortIntegersByLastDigit {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(24, 32, 45, 91, 57);
		System.out.println(numbers);
		numbers.sort(Comparator.comparingInt(num -> num % 10));
		System.out.println(numbers);
	}

}
