package com.shoppingService.streams.easy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Problem13RemoveEmptyStrings {
	public static void main(String[] args) {
		List<String> list = List.of("Java", "", "Streams", " ", "API");
		System.out.println(list);
		list.stream().filter(StringUtils::isNotBlank).toList().forEach(System.out::println);
	}
}
