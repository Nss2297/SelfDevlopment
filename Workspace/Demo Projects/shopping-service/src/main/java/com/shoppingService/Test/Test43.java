package com.shoppingService.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test43 {
	private static final Logger log = LoggerFactory.getLogger(Test43.class);

	public static void main(String[] args) {
		String input = "Saudi Arabia";
		String regexLeadingWhitespace = "\\s+.*";
		String regexTrailingWhitespace = ".*\\s+";

		boolean leadingMatches = Pattern.matches(regexLeadingWhitespace, input);
		boolean trailingMatches = Pattern.matches(regexTrailingWhitespace, input);
		log.info("Nationality:- {}", input);
		if(leadingMatches) {
			log.info("Leading space:- {}", leadingMatches);
		}
		if(trailingMatches) {
			log.info("Trailing space:- {}", trailingMatches);
		}
	}

}
