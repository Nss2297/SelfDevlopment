package com.shoppingService.streams;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstElementGreaterThanTen {
	public static void main(String[] args) {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 17, 18, 9);
		list.stream().filter(num -> num > 10).findFirst().ifPresentOrElse(num -> log.info("{}", num), null);
	}
}
