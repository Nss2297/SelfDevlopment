package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.pbmadminservice.enums.management.Gender;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidGender;

public class IsValidGenderValidator implements ConstraintValidator<IsValidGender, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(value)) {
			return false;
		} else {
			if (!value.equalsIgnoreCase(Gender.FEMALE.value()) && !value.equalsIgnoreCase(Gender.MALE.value())) {
				return false;
			}
		}
		return true;
	}

}
