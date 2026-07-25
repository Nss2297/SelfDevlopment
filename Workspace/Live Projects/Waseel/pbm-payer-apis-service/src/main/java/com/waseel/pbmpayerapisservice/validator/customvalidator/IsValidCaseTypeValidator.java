package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.CaseType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidCaseType;

public class IsValidCaseTypeValidator implements ConstraintValidator<IsValidCaseType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(CaseType.INPATIENT.value())
					&& !arg0.equalsIgnoreCase(CaseType.OUTPATIENT.value())) {
				return false;
			}
		}
		return true;
	}

}
