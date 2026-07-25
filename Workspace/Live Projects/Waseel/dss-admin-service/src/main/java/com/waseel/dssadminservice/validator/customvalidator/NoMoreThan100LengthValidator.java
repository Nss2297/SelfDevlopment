package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.NoMoreThan100Length;

public class NoMoreThan100LengthValidator implements ConstraintValidator<NoMoreThan100Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || StringUtils.strip(value).getBytes().length <= 100;
	}

}
