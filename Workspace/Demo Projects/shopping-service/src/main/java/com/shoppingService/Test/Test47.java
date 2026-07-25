package com.shoppingService.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test47 {
	private static final Logger log = LoggerFactory.getLogger(Test47.class);

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		log.info("{}-{}", LocalDate.parse("01-01-2589", formatter).getMonth(), LocalDate.parse("01-01-2589", formatter).getYear());
	}

}
