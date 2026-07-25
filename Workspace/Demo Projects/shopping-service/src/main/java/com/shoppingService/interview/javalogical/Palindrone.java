package com.shoppingService.interview.javalogical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Palindrone {

	private static final Logger log = LoggerFactory.getLogger(Palindrone.class);

	public static void main(String args[]) {
		int num = 10;
		log.info("Number:- [{}]", num);
		String number = String.valueOf(num);
		int numberLength = number.length();
		char firstCharacter = number.charAt(0);
		char lastCharacter = number.charAt(numberLength - 1);
		if (firstCharacter == lastCharacter) {
			log.info("[{}]", Boolean.TRUE);
			return;
		}
		log.info("[{}]", Boolean.FALSE);
	}
}
