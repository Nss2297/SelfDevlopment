package com.shoppingService.patterns.builder;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuilderDesignPattern {
	private static final Logger log = LoggerFactory.getLogger(BuilderDesignPattern.class);

	public static void main(String[] args) {
		Phone phone = new PhoneBuilder().setBattery(10000).setOs("IOS").setProcessor("SnapDragon 556")
				.setScreenSize(BigDecimal.TEN).setRam(300).getPhoneObject();
		log.info("{}", phone);
	}
}
