package com.shoppingService.Test;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test13 {
	private static final Logger log = LoggerFactory.getLogger(Test13.class);

	public static void main(String args[]) throws ParseException {
		String s = "19 8";
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(s);
		boolean found = matcher.find();
		boolean isWhitespace = s.matches("^\\s*$");
		log.info("{}-{}", found, isWhitespace);
	}
}
