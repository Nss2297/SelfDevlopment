package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test20 {
	private static final Logger log = LoggerFactory.getLogger(Test20.class);

	public static void main(String[] args) {
		List<String> list = new ArrayList();
		list.add("1");
		list.add("1");
		log.info(String.join(",", list));	
	}
}
