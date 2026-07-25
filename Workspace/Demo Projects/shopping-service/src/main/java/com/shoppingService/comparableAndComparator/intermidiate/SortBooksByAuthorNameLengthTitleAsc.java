package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortBooksByAuthorNameLengthTitleAsc {
	@Getter
	@AllArgsConstructor
	private static class Book {
		private String title;
		private String name;

		public String toString() {
			return this.name + "(" + this.title + ")";
		}
	}

	public static void main(String[] args) {
		List<Book> books = Arrays.asList(new Book("Java Basics", "Sam"), new Book("Advanced Java", "Jonathan"),
				new Book("Spring Boot", "Alice"), new Book("Microservices", "Tom"));
		System.out.println(books);
		books.stream()
				.sorted(Comparator.<Book>comparingInt(book -> book.getTitle().length()).thenComparing(Book::getName))
				.forEach(System.out::println);
	}
}
