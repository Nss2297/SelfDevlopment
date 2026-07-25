package com.waseel.emailservice.validator.customvalidator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.waseel.emailservice.validator.customannotation.ValidateWaseelEmail;

public class ValidateWaseelEmailValidator implements ConstraintValidator<ValidateWaseelEmail, String> {

	@Autowired
	private Environment environment;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		return !Arrays.asList(environment.getActiveProfiles()).contains("qa")||
				(!StringUtils.isBlank(value) && value.toLowerCase().contains("@waseel.com"));
	}
}
