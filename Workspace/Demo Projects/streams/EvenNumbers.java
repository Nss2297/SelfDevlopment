package com.shoppingService.streams;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EvenNumbers {
	public static void main(String[] args) {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Integer> evenList = list.stream().filter(num -> num % 2 == 0).collect(Collectors.toList());
		log.info("{}", evenList);
	}
}
