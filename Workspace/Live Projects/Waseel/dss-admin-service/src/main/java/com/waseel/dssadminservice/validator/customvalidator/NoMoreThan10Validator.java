package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.NoMoreThan10Length;

public class NoMoreThan10Validator implements ConstraintValidator<NoMoreThan10Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isBlank(value) || StringUtils.strip(value).getBytes().length <= 10;
	}
}
