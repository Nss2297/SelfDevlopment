package com.waseel.brservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.brservice.validator.customannotation.NoMoreThan100Length;

public class NoMoreThan100LengthValidator implements ConstraintValidator<NoMoreThan100Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 100;
	}
}
