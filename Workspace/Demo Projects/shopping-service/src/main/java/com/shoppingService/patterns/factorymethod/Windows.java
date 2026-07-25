package com.shoppingService.patterns.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Windows implements Os {
	private static final Logger log = LoggerFactory.getLogger(Windows.class);

	@Override
	public void specification() {
		log.info("Windows object.");
	}

}
