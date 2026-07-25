package com.shoppingService.patterns.factorymethod;

import java.math.BigDecimal;

public class IndustrialPlan extends Plan {

	@Override
	void getRate() {
		rate = BigDecimal.TEN;
	}

}
