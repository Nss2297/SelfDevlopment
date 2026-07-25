package com.shoppingService.Test;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test22 {
	private static final Logger log = LoggerFactory.getLogger(Test22.class);

	public static void main(String[] args) {
		log.info("{}", new BigDecimal(1).toString().equals("3"));	
	}
}
