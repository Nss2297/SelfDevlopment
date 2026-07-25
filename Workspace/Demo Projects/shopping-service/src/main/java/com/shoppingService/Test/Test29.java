package com.shoppingService.Test;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test29 {
	private static final Logger log = LoggerFactory.getLogger(Test29.class);

	public static void main(String[] args) {
		BigDecimal totalPrice = BigDecimal.valueOf(1).add(BigDecimal.valueOf(1));
		log.info("totalPrice: {}", totalPrice);
		setTotalPrice(totalPrice);
		log.info("totalPrice: {}", totalPrice);
	}

	private static void setTotalPrice(BigDecimal totalPrice) {
		for (int a = 0; a < 1; a++) {
			totalPrice = totalPrice.add(BigDecimal.valueOf(1));
		}
		log.info("totalPrice: {}", totalPrice);
	}
}
