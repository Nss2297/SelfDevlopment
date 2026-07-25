package com.shoppingService.comparableAndComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private Integer age;
	private String name;

	public String toString() {
		return this.age.toString() + ":" + this.name;
	}
}
