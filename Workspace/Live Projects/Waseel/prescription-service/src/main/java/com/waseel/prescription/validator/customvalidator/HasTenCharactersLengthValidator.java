package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.validator.customannotation.HasTenCharactersLength;

public class HasTenCharactersLengthValidator implements ConstraintValidator<HasTenCharactersLength, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isBlank(value) || value.trim().getBytes().length == 10;
	}
}
