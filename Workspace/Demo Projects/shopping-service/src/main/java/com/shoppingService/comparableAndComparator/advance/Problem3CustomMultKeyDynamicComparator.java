package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem3CustomMultKeyDynamicComparator {
	@Getter@AllArgsConstructor
	private static class Person{
		private String name;
		private Integer age;
		public String toString() {
		return this.name+"("+this.age+")";
		}
	}
public static void main(String[] args) {
	List<Person> people = Arrays.asList(
		    new Person("Alice", 30),
		    new Person("Bob", 25),
		    new Person("Charlie", 35)
		);
		String sortBy = "age"; // dynamically chosen
//		String sortBy = "name"; // dynamically chosen
	Map<String, Comparator<Person>> map=new HashMap<>();
	map.put("age", Comparator.comparing(Person::getAge));
	map.put("name", Comparator.comparing(Person::getName));
System.out.println(people);	
people.sort(map.get(sortBy));
System.out.println(people);	
} 
}
