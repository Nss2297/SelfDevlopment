package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan30Length;

public class NoMoreThan30LengthValidator implements ConstraintValidator<NoMoreThan30Length, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length <= 30;
	}
}
