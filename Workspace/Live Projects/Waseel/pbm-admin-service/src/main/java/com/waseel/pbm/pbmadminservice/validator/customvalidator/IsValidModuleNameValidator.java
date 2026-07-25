package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.enums.ModuleName;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidModuleName;

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
