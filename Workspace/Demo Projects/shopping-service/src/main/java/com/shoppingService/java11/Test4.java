package com.shoppingService.java11;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

public class Test4 {

	public static void main(String args[]) {
		List<String> lists = Arrays.asList("1SGSDF", "2SGSDF", "3SGSDF");
		String listString = lists.stream().map((@NonNull var a) -> a.toLowerCase()).collect(Collectors.joining(","));
		System.out.println(listString);
	}
}
