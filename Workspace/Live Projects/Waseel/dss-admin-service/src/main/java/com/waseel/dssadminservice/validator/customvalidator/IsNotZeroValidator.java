package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.validator.customannotation.IsNotZero;

public class IsNotZeroValidator implements ConstraintValidator<IsNotZero, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !value.trim().equals("0");
	}

}
