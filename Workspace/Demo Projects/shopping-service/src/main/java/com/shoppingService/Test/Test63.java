package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test63 {
	private static final Logger log = LoggerFactory.getLogger(Test63.class);

	public static void main(String[] args) {
		Test63 test = new Test63();
		int sum = 0, num = 6, divisor = 1;
		while (divisor < num + 1) {
			if (num % divisor == 0) {
				sum = sum + divisor;
			}
			++divisor;
		}
		log.info("{}", sum);
	}

}
