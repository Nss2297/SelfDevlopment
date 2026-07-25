package com.shoppingService.comparableAndComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortNumbersEvenFirstOddLater {
	private static class OddEvenComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
//			return o1 % 2 == 0 && o2 % 2 != 0 ? -1 : o1 % 2 != 0 && o2 % 2 == 0 ? 1 : 0;
			return o1 % 2 == 0? -1 : o1 % 2 != 0? 1 : 0;
		}
	}

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,10);
		System.out.println(list);
		list.sort(new OddEvenComparator());
		System.out.println(list);
	}
}
