package com.shoppingService.comparableAndComparator.arrays;

import java.util.Arrays;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class SortByComparator {
	@AllArgsConstructor
	@ToString
	private static class Triplet {
		int a, s, d;
	}

	private static class CompareArrays implements Comparator<Triplet> {
		@Override
		public int compare(Triplet o1, Triplet o2) {
			return o1.d - o2.d;
		}

	}

	public static void main(String[] args) {
		Triplet[] tripletArray = new Triplet[4];
		tripletArray[0] = new Triplet(1, 2, 3);
		tripletArray[1] = new Triplet(2, 2, 4);
		tripletArray[2] = new Triplet(5, 6, 1);
		tripletArray[3] = new Triplet(10, 2, 10);
		Arrays.sort(tripletArray, new CompareArrays());
		for (int f = 0; f < tripletArray.length; f++) {
			System.out.println(tripletArray[f].toString());
		}
	}
}
