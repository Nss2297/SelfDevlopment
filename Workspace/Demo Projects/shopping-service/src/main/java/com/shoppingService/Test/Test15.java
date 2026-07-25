package com.shoppingService.Test;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test15 {
	private static final Logger log = LoggerFactory.getLogger(Test15.class);

	public static void main(String[] args) {
		String bd = "1234567890.1234567890";
		Double db = Double.parseDouble(bd);
		BigDecimal num1 = new BigDecimal("2.999");
		BigDecimal num2 = new BigDecimal("50");
		BigDecimal product = num1.multiply(num2); 
//		log.info("Double:-{}, BigDecimal:-{}", db, num);
		log.info("product:-{}", product);
	}
}
