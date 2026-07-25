package com.shoppingService.comparableAndComparator.advance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem7HierarchicalSortingWithTieBreakerViaSublistAverage {
	@Getter@AllArgsConstructor
private static class Student{
	private String name;
	private List<Integer> marks;
	public String toString() {
		return this.name+"("+this.marks+")";
	}
}
	public static void main(String[] args) {
		
	}
}
