package com.shoppingService.Test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test18 {
	private static final Logger log = LoggerFactory.getLogger(Test18.class);

	public static void main(String[] args) {
		List<String> list = null;
		if(!list.isEmpty()) {
		log.info("not empty");	
		}else {
			log.info("empty");
		}
	}
}
