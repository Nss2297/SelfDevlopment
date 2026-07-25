package com.shoppingService.java11;

import lombok.extern.slf4j.Slf4j;

interface Calculate {
	int sum(int a, int s);
}

@Slf4j
public class TypeInference {
	public static void main(String[] args) {
		Calculate calculate = (var a, var s) -> a + s;
		log.info("{}", calculate.sum(1, 20));
	}
}
