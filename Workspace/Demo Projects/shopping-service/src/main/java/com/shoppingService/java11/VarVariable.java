package com.shoppingService.java11;

import java.util.List;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VarVariable {
	public static void main(String[] args) {
		List<String> names1 = List.of("One", "Two");
		List<String> names2 = List.of("Three", "Four");
		final var names = List.of(names1, names2);
		names.forEach(name -> {
			log.info("{}", name);
		});

		var stream = List.of(1, 2, 3, 4).stream().filter(num -> num % 2 == 0);
		stream.forEach(num -> {
			log.info("{}", num);
		});
	}
}
