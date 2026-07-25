package com.shoppingService.comparableAndComparator.arrays;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class SortByComparable {
	@AllArgsConstructor
	@ToString
	private static class Triplet implements Comparable<Triplet> {
		int a, s, d;

		@Override
		public int compareTo(Triplet triplet) {
			return this.d - triplet.d;
		}
	}

	public static void main(String[] args) {
		Triplet[] tripletArray=new Triplet[4];
		tripletArray[0]=new Triplet(1, 2, 3);
		tripletArray[1]=new Triplet(2, 2, 4);
		tripletArray[2]=new Triplet(5, 6, 1);
		tripletArray[3]=new Triplet(10, 2, 10);
		for(int f=0;f<tripletArray.length;f++) {
			System.out.println(tripletArray[f].toString());
		}
		Arrays.sort(tripletArray);
		for(int f=0;f<tripletArray.length;f++) {
			System.out.println(tripletArray[f].toString());
		}
	}
}
