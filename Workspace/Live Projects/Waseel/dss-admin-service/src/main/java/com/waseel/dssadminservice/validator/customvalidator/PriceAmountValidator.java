package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.validator.customannotation.PriceAmount;

public class PriceAmountValidator implements ConstraintValidator<PriceAmount, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || !value.contains(".")) {
			return false;
		}
		return value.matches("^[0-9]+(\\.[0-9]+){0,1}$");
	}

}
