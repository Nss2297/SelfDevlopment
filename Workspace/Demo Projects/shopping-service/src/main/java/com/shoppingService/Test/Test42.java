package com.shoppingService.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test42 {
	private static final Logger log = LoggerFactory.getLogger(Test42.class);

	public static void main(String[] args) {
		String inputString = "Hello123";
		  Pattern pattern = Pattern.compile("\\d");
	        Matcher matcher = pattern.matcher(inputString);
		log.info("{}", matcher.find());
	}

}
