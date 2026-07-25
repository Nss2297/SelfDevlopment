package com.waseel.pbmnotificationservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbmnotificationservice.validator.customannotation.NoMoreThan15Length;

public class NoMoreThan15LengthValidator implements ConstraintValidator<NoMoreThan15Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 15;
	}
}
