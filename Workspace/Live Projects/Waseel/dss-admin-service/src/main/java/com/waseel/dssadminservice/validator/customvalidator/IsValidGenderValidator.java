package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.enums.Gender;
import com.waseel.dssadminservice.validator.customannotation.IsValidGender;

public class IsValidGenderValidator implements ConstraintValidator<IsValidGender, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {

		if (value == null || value.isEmpty() || value.trim().getBytes().length > 20) {
			return false;
		}
		return value.equalsIgnoreCase(Gender.FEMALE.value())
				|| value.equalsIgnoreCase(Gender.MALE.value());
	}
}
