package com.shoppingService.patterns.builder;

import java.math.BigDecimal;

public class Phone {
	private String os;
	private Integer ram;
	private String processor;
	private BigDecimal screenSize;
	private Integer battery;

	public Phone() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Phone(String os, Integer ram, String processor, BigDecimal screenSize, Integer battery) {
		super();
		this.os = os;
		this.ram = ram;
		this.processor = processor;
		this.screenSize = screenSize;
		this.battery = battery;
	}

	@Override
	public String toString() {
		return "Phone [os=" + os + ", ram=" + ram + ", processor=" + processor + ", screenSize=" + screenSize
				+ ", battery=" + battery + "]";
	}

}
