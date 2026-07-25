package com.shoppingService.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test48 {
	private static final Logger log = LoggerFactory.getLogger(Test48.class);

	public static void main(String[] args) {
		String number1 = "123.45";
		String number2 = "678";
//		log.info("{} has decimal: {}", number1, hasDecimal(number1));
//		log.info("{} has decimal: {}", number2, hasDecimal(number2));
		log.info("{} has decimal: {}", number1, !Pattern.compile("\\d+\\.\\d*|\\d*\\.\\d+").matcher(number1).matches());
	}

	private static boolean hasDecimal(String number) {
		// Regular expression to match a number with optional decimal part
		String regex = "\\d+\\.\\d*|\\d*\\.\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);

		// If a match is found, the number has a decimal part
		return matcher.matches();
	}
}
