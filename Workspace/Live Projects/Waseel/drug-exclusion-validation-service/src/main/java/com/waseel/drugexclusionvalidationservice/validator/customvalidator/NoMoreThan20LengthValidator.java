package com.waseel.drugexclusionvalidationservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.drugexclusionvalidationservice.validator.customannotation.NoMoreThan20Length;

public class NoMoreThan20LengthValidator implements ConstraintValidator<NoMoreThan20Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 20;
	}
}
