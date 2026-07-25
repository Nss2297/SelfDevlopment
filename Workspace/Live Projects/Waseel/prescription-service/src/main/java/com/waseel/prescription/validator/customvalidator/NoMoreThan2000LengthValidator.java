package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.validator.customannotation.NoMoreThan2000Length;

public class NoMoreThan2000LengthValidator implements ConstraintValidator<NoMoreThan2000Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 2000;
	}
}
