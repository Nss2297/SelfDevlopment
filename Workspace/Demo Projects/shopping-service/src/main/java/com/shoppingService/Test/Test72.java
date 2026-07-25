package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test72 {
	public static void main(String[] args) {
		List<String> list1 = List.of("Java", "Streams", "Practice");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		String joinedString = list1.stream().collect(Collectors.joining());
		System.out.println("Joing: " + joinedString);
	}
}
