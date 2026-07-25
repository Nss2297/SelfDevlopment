package com.shoppingService.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test53 {
	private static final Logger log = LoggerFactory.getLogger(Test53.class);

	public static void main(String[] args) {
		Test53 test53 = new Test53();
		BigDecimal packWeight = new BigDecimal(0.415);
		BigDecimal quantity = new BigDecimal(2);
		BigDecimal storagePackQty = new BigDecimal(1);
		BigDecimal weight = test53.multiply(2, packWeight, quantity, storagePackQty);
		log.info("{}", weight);
	}

	public BigDecimal multiply(int scale, BigDecimal... values) {
		BigDecimal currenValue = null;
		if ((values != null)) {
			if (values.length == 1) {
				currenValue = values[0];
			} else {
				if (values.length > 1) {
					currenValue = new BigDecimal(1);
					for (int i = 0; i < values.length; i++) {
						currenValue = multiply(values[i], currenValue, scale);
					}
				}
			}
		}
		currenValue = divide(currenValue, new BigDecimal(2), 2);
		return currenValue;
	}

	public BigDecimal multiply(BigDecimal first, BigDecimal second, int scale) {
//		return first.multiply(second).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return first.multiply(second).setScale(scale, RoundingMode.HALF_UP);
	}

	public BigDecimal divide(BigDecimal first, BigDecimal second, int scale) {
//		return first.divide(second, scale, BigDecimal.ROUND_HALF_UP);
		return first.divide(second, scale, RoundingMode.HALF_UP);
	}
}
