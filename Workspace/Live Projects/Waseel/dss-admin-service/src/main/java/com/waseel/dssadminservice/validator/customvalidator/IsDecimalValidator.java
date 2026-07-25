package com.waseel.dssadminservice.validator.customvalidator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.IsDecimal;

public class IsDecimalValidator implements ConstraintValidator<IsDecimal, String> {

	@Override
	public boolean isValid(String ageInDays, ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(ageInDays)) {
			return !Pattern.compile("\\d+\\.\\d*|\\d*\\.\\d+").matcher(ageInDays).matches();
		}
		return true;
	}
}
