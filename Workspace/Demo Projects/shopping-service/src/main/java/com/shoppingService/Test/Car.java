package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Car {
	private static final Logger log = LoggerFactory.getLogger(Car.class);
	private Engine engine;

//	@Autowired
	public Car(Engine engin) {
		super();
		this.engine = engin;
	}

	public void startCar() {
		engine.start();
		log.info("Car invoked.");
	}
}
