package com.shoppingService.comparableAndComparator;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private String name;
	private BigDecimal price;

	public String toString() {
		return this.name + "(" + this.price + ")";
	}
}
