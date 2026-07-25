package com.waseel.pbm.dssservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThanTwentyLength;

public class NoMoreThanTwentyLengthValidator implements ConstraintValidator<NoMoreThanTwentyLength, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 20;
	}
}
