package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test50 {
	private static final Logger log = LoggerFactory.getLogger(Test50.class);

	public static void main(String[] args) {
		String number1 = "test";
		String number2 = new String("test");
		log.info("{}", number1.equals(number2));
		log.info("{}", number1.hashCode());
		log.info("{}", number2.hashCode());
		log.info("{}", number1 == number2);
	}

}
