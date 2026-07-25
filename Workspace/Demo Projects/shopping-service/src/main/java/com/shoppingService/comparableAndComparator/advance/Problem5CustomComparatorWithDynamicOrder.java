package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem5CustomComparatorWithDynamicOrder {
	@Getter
	@AllArgsConstructor
	private static class Book {
		private String title;
		private String author;

		public String toString() {
			return this.author + "(" + this.title + ")";
		}
	}

public static void main(String[] args) {
	List<Book> books = Arrays.asList(
		    new Book("Java", "Alice"),
		    new Book("Python", "Bob"),
		    new Book("C++", "Charlie")
		);
	String sortBy="author";
//Comparator<Book> comparator=switch(sortBy){
//	case: "title"->Comparator.comparing(Book::getTitle);
//	case: "author"->Comparator.comparing(Book::getAuthor);
//	defalt->(b1,b2)=0;
//};
//books.sort(comparator);
}
}
