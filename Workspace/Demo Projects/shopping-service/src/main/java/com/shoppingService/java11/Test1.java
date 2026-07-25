package com.shoppingService.java11;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Test1 {

	public static void main(String args[]) {
		String lines = "Baeldung helps \n \n developers \n explore Java.";
		List<String> linesData = lines.lines().filter(line -> StringUtils.isNotBlank(line)).map(String::strip)
				.collect(Collectors.toList());
		System.out.println(linesData);
	}
}
