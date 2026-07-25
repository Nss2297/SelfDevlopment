package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Test61 {
	private static final Logger log = LoggerFactory.getLogger(Test61.class);
	@Autowired
	private static Engine engine ;
	
	public static void main(String[] args) {
//		Test61 test = new Test61();
		Car car = new Car(engine);
		car.startCar();
		log.info("Test61 invoked.");
	}

}
