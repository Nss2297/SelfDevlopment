package com.shoppingService.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test23 {
	private static final Logger log = LoggerFactory.getLogger(Test23.class);

	public static void main(String[] args) {
		List<String> list = null;
		log.info("null=>{} empty=>{}", null == list, list.isEmpty());	
	}
}
