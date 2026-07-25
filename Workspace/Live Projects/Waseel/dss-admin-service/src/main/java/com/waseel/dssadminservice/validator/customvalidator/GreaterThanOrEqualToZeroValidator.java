package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.validator.customannotation.GreaterThanOrEqualToZero;

public class GreaterThanOrEqualToZeroValidator implements ConstraintValidator<GreaterThanOrEqualToZero, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !value.trim().equals("0");
	}

}
