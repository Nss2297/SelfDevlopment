package com.shoppingService.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test51 {
	private static final Logger log = LoggerFactory.getLogger(Test51.class);

	public static void main(String[] args) {
		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(1));
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(45));
		list.add(new BigDecimal(16));
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(78));
		list.add(new BigDecimal(0));

		log.info("{}", list);
		list.removeIf(item -> (item.compareTo(BigDecimal.ZERO) == 0));
		log.info("{}", list);
	}

}
