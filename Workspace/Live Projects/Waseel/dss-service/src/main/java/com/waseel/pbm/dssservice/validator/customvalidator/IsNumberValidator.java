package com.waseel.pbm.dssservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.dssservice.validator.customannotation.IsNumber;

public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		try {

			if (!StringUtils.isBlank(arg0)) {
				if (arg0.contains(".") || arg0.contains(",")) {
					Double.parseDouble(arg0);
				} else {
					Integer.parseInt(arg0);
				}
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}