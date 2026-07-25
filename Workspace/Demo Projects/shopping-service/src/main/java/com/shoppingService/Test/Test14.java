package com.shoppingService.Test;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test14 {
	private static final Logger log = LoggerFactory.getLogger(Test14.class);

	public static void main(String args[]) throws ParseException {
		try {
			String s = "12.058";
			int asd = s.length();
			String a = s.split("\\.")[0];
			log.info("{}-{}", s, a);
		} catch (Exception ex) {
			log.info("{}", ex);
		}
	}
}
