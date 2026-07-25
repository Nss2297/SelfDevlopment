package com.shoppingService.patterns.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainOs {
	private static final Logger log = LoggerFactory.getLogger(MainOs.class);

	public static void main(String[] args) {
		OsFactory osFactory = new OsFactory();
		Os os = osFactory.fetchOs("andriod");
		if (null != os) {
			os.specification();
		} else {
			log.error("No object.");
		}
	}
}
