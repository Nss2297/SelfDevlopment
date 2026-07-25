package com.shoppingService.streams;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringIntoUpperCase {
	public static void main(String[] args) {
		List<String> list = List.of("geeks", "forgeeks", "a computer portal");
		List<String> uppserCaseList = list.stream().map(str -> str.toUpperCase()).collect(Collectors.toList());
		log.info("{}", uppserCaseList);
	}
}
