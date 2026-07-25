package com.shoppingService.Test;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class Test35 {
	private static final Logger log = LoggerFactory.getLogger(Test35.class);

	public static void main(String[] args) {
		int statusCodeValue = 200;
		log.info("{}", statusCodeValue==org.springframework.http.HttpStatus.OK.value());
	}

}
