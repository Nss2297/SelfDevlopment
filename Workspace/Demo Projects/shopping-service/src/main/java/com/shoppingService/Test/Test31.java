package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test31 {
	private static final Logger log = LoggerFactory.getLogger(Test31.class);

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		log.info("IsEmpty: {}", list.isEmpty());
	}

}
