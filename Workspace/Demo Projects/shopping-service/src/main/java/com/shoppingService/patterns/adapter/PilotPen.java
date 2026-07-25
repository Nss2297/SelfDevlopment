package com.shoppingService.patterns.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PilotPen {

	public void mark(String data) {
		log.info("Assignment data from Pilot pen class.");
		log.info("{}", data);
	}
}
