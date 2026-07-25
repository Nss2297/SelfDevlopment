package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.validator.customannotation.ValidGranularUnit;

public class ValidGranularUnitValidation implements ConstraintValidator<ValidGranularUnit, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			if (value != null) {
				int number = Integer.parseInt(value);
				return number >= 1 && number <= 9999;
			}
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
