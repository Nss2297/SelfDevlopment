package com.shoppingService.Test;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test17 {
	private static final Logger log = LoggerFactory.getLogger(Test17.class);

	public static void main(String[] args) {
		log.info("value=>{}", new BigDecimal(20000).compareTo(new BigDecimal(0000)) >=0 );
	}
}
