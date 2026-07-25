package com.shoppingService.Test;

import static java.util.stream.Collectors.counting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test93 {

	public static void main(String[] args) {
		String paragraph = "Java streams are powerful. Streams make coding easier.";
		List<String> list1 = Arrays.asList(paragraph.toLowerCase().replace(".", "").split(" "));
		System.out.println("List: " + list1);
		Map<String, Long> map = list1.stream().collect(Collectors.groupingBy(word -> word, counting()));
		System.out.println(map);
	}
}
