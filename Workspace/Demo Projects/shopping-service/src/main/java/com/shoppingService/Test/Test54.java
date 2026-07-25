package com.shoppingService.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test54 {
	private static final Logger log = LoggerFactory.getLogger(Test54.class);

	public static void main(String[] args) {
//		BigDecimal weight = new BigDecimal(0.415).multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_UP);
//		BigDecimal weight = new BigDecimal(2).multiply(new BigDecimal(0.42)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal weight = new BigDecimal(1).multiply(new BigDecimal(0.84)).setScale(2, RoundingMode.HALF_UP);
		log.info("{}", weight);
	}

}
