package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test5 {
	public static void main(String args[]) {
		List<Product> productsList = new ArrayList<Product>();
		// Adding Products
		productsList.add(new Product(1, "HP Laptop", 25000f));
		productsList.add(new Product(2, "Dell Laptop", 30000f));
		productsList.add(new Product(3, "Lenevo Laptop", 28000f));
		productsList.add(new Product(4, "Sony Laptop", 28000f));
		productsList.add(new Product(5, "Apple Laptop", 90000f));

		List<Product> list = (List<Product>) productsList.stream().filter(p -> p.getC() < 90000f);
		System.out.println(list.toString());
	}
}
