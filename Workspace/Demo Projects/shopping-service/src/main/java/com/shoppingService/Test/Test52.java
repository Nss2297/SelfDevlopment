package com.shoppingService.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test52 {
	private static final Logger log = LoggerFactory.getLogger(Test52.class);

	public static void main(String[] args) {
		log.info("{}", BigDecimal.ONE.compareTo(BigDecimal.TEN) == -1);
		log.info("{}", BigDecimal.ONE.compareTo(BigDecimal.TEN) < 0);
	}

}
