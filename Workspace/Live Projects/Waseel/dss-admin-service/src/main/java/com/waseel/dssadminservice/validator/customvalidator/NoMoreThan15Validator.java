package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.NoMoreThan15Length;

public class NoMoreThan15Validator implements ConstraintValidator<NoMoreThan15Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null ||  StringUtils.isBlank(value) || StringUtils.strip(value).getBytes().length <= 15;
	}

}
