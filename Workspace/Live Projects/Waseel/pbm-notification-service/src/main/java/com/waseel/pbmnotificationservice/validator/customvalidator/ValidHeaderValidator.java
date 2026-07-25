package com.waseel.pbmnotificationservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmnotificationservice.validator.customannotation.ValidHeader;

public class ValidHeaderValidator implements ConstraintValidator<ValidHeader, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !value.isEmpty() && !value.equalsIgnoreCase("null");
	}
}
