package com.shoppingService.patterns.factorymethod;

import java.math.BigDecimal;

public class CommercialPlan extends Plan {

	@Override
	void getRate() {
		rate = BigDecimal.valueOf(2L);
	}

}
