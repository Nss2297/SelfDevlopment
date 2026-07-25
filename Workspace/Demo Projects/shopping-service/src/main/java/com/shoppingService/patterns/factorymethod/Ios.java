package com.shoppingService.patterns.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ios implements Os {
	private static final Logger log = LoggerFactory.getLogger(Ios.class);

	@Override
	public void specification() {
		log.info("Ios object.");
	}

}
