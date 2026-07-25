package com.shoppingService.patterns.factorymethod;

import java.math.BigDecimal;

public abstract class Plan {

	protected BigDecimal rate;

	abstract void getRate();

	public void calculateBill(int units) {
		System.out.println(rate.multiply(new BigDecimal(units)));
	}

}
