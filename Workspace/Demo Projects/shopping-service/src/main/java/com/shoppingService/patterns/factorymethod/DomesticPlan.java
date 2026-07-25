package com.shoppingService.patterns.factorymethod;

import java.math.BigDecimal;

public class DomesticPlan extends Plan {

	@Override
	void getRate() {
		rate = BigDecimal.ONE;
		
	}

}
