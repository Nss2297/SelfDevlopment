package com.waseel.prescription.validator.customvalidator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacterAtBeginning;

public class NoWhiteSpaceCharacterAtBeginningValidator
		implements ConstraintValidator<NoWhiteSpaceCharacterAtBeginning, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.isBlank(value)) {
			return !Pattern.matches("\\s+.*", value);
		}
		return true;
	}
}
