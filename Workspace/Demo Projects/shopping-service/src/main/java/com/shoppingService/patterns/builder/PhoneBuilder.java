package com.shoppingService.patterns.builder;

import java.math.BigDecimal;

public class PhoneBuilder {
	private String os;
	private Integer ram;
	private String processor;
	private BigDecimal screenSize;
	private Integer battery;

	public PhoneBuilder setOs(String os) {
		this.os = os;
		return this;
	}

	public PhoneBuilder setRam(Integer ram) {
		this.ram = ram;
		return this;
	}

	public PhoneBuilder setProcessor(String processor) {
		this.processor = processor;
		return this;
	}

	public PhoneBuilder setScreenSize(BigDecimal screenSize) {
		this.screenSize = screenSize;
		return this;
	}

	public PhoneBuilder setBattery(Integer battery) {
		this.battery = battery;
		return this;
	}

	public Phone getPhoneObject() {
		return new Phone(os, ram, processor, screenSize, battery);
	}
}
