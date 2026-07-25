package com.waseel.pbm.dssservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.dssservice.validator.customannotation.NoLessThanThreeLength;

public class NoLessThanThreeValidator implements ConstraintValidator<NoLessThanThreeLength, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return StringUtils.isBlank(arg0) || arg0.trim().getBytes().length >= 3;
	}
}
