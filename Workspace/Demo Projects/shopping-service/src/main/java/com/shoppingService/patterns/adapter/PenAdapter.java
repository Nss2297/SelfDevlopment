package com.shoppingService.patterns.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PenAdapter implements Pen {

	private PilotPen pilotPen = new PilotPen();

	@Override
	public void writeOperation(String data) {
		log.info("Invoked PenAdapter.");
		pilotPen.mark(data);
	}

}
