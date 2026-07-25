package com.waseel.pbmpayerapisservice.validator.customvalidator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.validator.customannotation.NoSpecialCharacter;

public class NoSpecialCharacterValidator implements ConstraintValidator<NoSpecialCharacter, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !Pattern.compile("[~`!@#$%^&/*()=+{}|_:;',<.>?\\-\\[\\]\\\"\\\\]").matcher(value).find();
	}
}
