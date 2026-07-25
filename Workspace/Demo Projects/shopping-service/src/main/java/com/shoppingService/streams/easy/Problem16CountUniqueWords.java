package com.shoppingService.streams.easy;

import java.util.List;

public class Problem16CountUniqueWords {
public static void main(String[] args) {
	List<String> words = List.of("apple", "banana", "apple", "orange", "banana", "grape");
System.out.println(words);
long count=words.stream().distinct().count();
System.out.println(count);
}
}
