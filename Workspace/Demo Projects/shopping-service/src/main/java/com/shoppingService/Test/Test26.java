package com.shoppingService.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test26 {
	private static final Logger log = LoggerFactory.getLogger(Test26.class);

	public static void main(String[] args) {
		String input = "0098789654123+00";
		 // Compile regular expression
        final Pattern pattern = Pattern.compile("[^00/+]", Pattern.CASE_INSENSITIVE);
//        // Match regex against input
        final Matcher matcher = pattern.matcher(input);
        // Use results...
		log.info("{}", matcher.find());	
		log.info("{}", input.replaceAll("^[/+]", "").replaceFirst("00", ""));
	}
}
