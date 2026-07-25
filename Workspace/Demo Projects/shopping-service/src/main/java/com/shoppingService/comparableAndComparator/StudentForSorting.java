package com.shoppingService.comparableAndComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentForSorting {
	protected String name;
	protected Long rollNo;

	public String toString() {
		return this.rollNo.toString() + ":" + this.name;
	}
}
