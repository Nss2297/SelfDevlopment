package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Engine {
	private static final Logger log = LoggerFactory.getLogger(Engine.class);

	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.start();
	}

	public void start() {
		log.info("Engine start invoked.");
	}
}
