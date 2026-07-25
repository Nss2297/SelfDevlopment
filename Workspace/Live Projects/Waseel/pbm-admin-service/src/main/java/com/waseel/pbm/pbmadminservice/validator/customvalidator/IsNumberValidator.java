package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true; // Allow null values
		}
		return value.trim().matches("^\\d+$");
	}
}
