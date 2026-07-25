package com.waseel.pbm.dssservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThanTenLength;

public class NoMoreThanTenValidator implements ConstraintValidator<NoMoreThanTenLength, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return StringUtils.isBlank(arg0) || arg0.trim().getBytes().length <= 10;
	}
}
