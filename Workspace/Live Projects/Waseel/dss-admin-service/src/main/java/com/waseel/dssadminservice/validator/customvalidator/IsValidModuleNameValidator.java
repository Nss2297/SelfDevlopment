package com.waseel.dssadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.dssadminservice.enums.ModuleName;
import com.waseel.dssadminservice.validator.customannotation.IsValidModuleName;

public class IsValidModuleNameValidator implements ConstraintValidator<IsValidModuleName, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		if (value == null || value.isEmpty() || value.trim().getBytes().length > 20) {
			return false;
		}
		return value.equalsIgnoreCase(ModuleName.ALL.value()) || value.equalsIgnoreCase(ModuleName.FDB.value())
				|| value.equalsIgnoreCase(ModuleName.IDF.value());
	}
}
