package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test33 {
	private static final Logger log = LoggerFactory.getLogger(Test33.class);

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		list1.add("4");
		List<String> list2 = new ArrayList<>();
		list2.add("1");
		list2.add("2");
		list1.stream().forEach(list1Data -> list2.stream().filter(list2Data -> list2Data.equals(list1Data)).findAny()
				.ifPresent(list2Data -> log.info("Same data: {}", list2Data)));
	}

}
