package com.shoppingService.Test;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test21 {
	private static final Logger log = LoggerFactory.getLogger(Test21.class);

	public static void main(String[] args) {
		log.info("{}", new BigDecimal(1).compareTo(new BigDecimal("sdf")) == 0);	
	}
}
