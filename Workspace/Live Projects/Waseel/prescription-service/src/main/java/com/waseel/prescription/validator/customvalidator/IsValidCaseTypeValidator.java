package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.validator.customannotation.IsValidCaseType;

public class IsValidCaseTypeValidator implements ConstraintValidator<IsValidCaseType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(BenefitCaseType.INPATIENT.toString())
					&& !arg0.equalsIgnoreCase(BenefitCaseType.OUTPATIENT.toString())) {
				return false;
			}
		}
		return true;
	}

}
