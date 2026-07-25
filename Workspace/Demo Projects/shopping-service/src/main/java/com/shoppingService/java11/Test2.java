package com.shoppingService.java11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Test2 {

	public static void main(String args[]) {
		List<String> lists = Arrays.asList("1", "2", "3");
		System.out.println(lists);
		String[] array = lists.toArray(String[]::new);
		for (String st : array) {
			System.out.println(st);
		}
	}
}
