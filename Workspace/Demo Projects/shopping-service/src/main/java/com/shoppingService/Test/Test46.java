package com.shoppingService.Test;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test46 {
	private static final Logger log = LoggerFactory.getLogger(Test46.class);

	public static void main(String[] args) {
		log.info("{}",
				!Pattern.compile("[^0-9.]").matcher("4").find() && !Pattern.compile("[^0-9.]").matcher("sdfsd").find());
	}

}
