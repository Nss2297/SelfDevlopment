package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Test27 {
	private static final Logger log = LoggerFactory.getLogger(Test27.class);

	public static void main(String[] args) {
		Object object = new Object();
		 String entity = new Gson().toJson(object);
		log.info("{}", entity);	
	}
}
