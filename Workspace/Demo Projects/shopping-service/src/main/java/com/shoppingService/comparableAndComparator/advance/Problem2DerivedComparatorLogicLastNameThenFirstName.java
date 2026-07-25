package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem2DerivedComparatorLogicLastNameThenFirstName {
	@Getter
	@AllArgsConstructor
	static class Person {
		private String fullName;

		public String toString() {
			return this.fullName;
		}
	}

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(new Person("Alice Johnson"), new Person("Bob Smith"),
				new Person("Charlie Brown"), new Person("David Smith"), new Person("Emma Johnson"));
		System.out.println(people);
		people.sort(Comparator.comparing((Person p) -> p.getFullName().split(" ")[1])
				.thenComparing((Person p) -> p.getFullName().split(" ")[0], Comparator.reverseOrder()));
		System.out.println(people);
	}
}
