package com.shoppingService.java11;

import java.util.List;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NorPredicate {
	public static void main(String[] args) {
		List<String> list = List.of("A", "\n \n", "s", " ");
		log.info("{}", list);
		Predicate<String> predicate = String::isBlank;
		list.stream().filter(Predicate.not(predicate)).forEach(string -> {
			log.info("{}", string);
		});
	}
}
