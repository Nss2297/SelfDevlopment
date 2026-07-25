package com.shoppingService.patterns.prototype;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Shop implements Cloneable {
	private String shopName;
	private List<Book> books = new ArrayList<>();

	public void fetchBooks() {
		for (int i = 1; i < 11; i++) {
			Book book = new Book(i, "Book " + i);
			getBooks().add(book);
		}
	}

	@Override
	protected Shop clone() throws CloneNotSupportedException {
		Shop shop = new Shop();
		for (Book book : this.getBooks()) {
			shop.getBooks().add(book);
		}
		return shop;
	}

}
