package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test66 {
	public static void main(String[] args) {
		List<String> list = List.of("alice", "bob", "charlie");
		System.out.println("List: " + Arrays.toString(list.toArray()));
		List<String> list2 = list.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println("List2: " + Arrays.toString(list2.toArray()));
	}
}
