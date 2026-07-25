package com.shoppingService.java11;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Test3 {

	public static void main(String args[]) {
		List<String> lists = Arrays.asList("1", "2", "3", "\n \n", "");
	     List<String> list = lists.stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
	     System.out.println(list);
	}
}
