package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.validator.customannotation.NoMoreThanFifteenLength;

public class NoMoreThanFifteenLengthValidator implements ConstraintValidator<NoMoreThanFifteenLength, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 15;
	}
}
