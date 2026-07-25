package com.shoppingService.comparableAndComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	private String name;
	private Integer marks;

	public String toString() {
		return this.name + "(" + this.marks + ")";
	}
}
