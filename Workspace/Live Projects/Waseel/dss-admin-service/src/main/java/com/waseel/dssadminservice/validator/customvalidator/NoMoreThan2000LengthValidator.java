package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.NoMoreThan2000Length;

public class NoMoreThan2000LengthValidator implements ConstraintValidator<NoMoreThan2000Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || StringUtils.strip(value).getBytes().length <= 2000;
	}

}
