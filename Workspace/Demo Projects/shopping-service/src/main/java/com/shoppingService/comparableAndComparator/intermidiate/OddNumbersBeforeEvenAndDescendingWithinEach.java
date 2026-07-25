package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OddNumbersBeforeEvenAndDescendingWithinEach {
	private static class ComparingOddEvenNums implements Comparator<Integer> {
		@Override
		public int compare(Integer num1, Integer num2) {
			return num1 % 2 != 0 ? -1 : num1 % 2 == 0 ? 1 : 0;
		}
	}

public static void main(String[] args) {
	List<Integer> list = Arrays.asList(10, 3, 7, 2, 9, 4, 11, 6);
	System.out.println(list);
	list.sort(new ComparingOddEvenNums());
	System.out.println(list);
	list.sort(Comparator.<Integer>comparingInt(num -> num % 2 != 0 ? -1 : num % 2 == 0 ? 1 : 0)
			.thenComparing(Comparator.reverseOrder()));
	System.out.println(list);
}
}
