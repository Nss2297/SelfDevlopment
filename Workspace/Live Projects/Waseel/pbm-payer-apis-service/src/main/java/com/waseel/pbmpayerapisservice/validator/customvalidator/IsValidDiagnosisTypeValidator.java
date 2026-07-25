package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.DiagnosisType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidDiagnosisType;

public class IsValidDiagnosisTypeValidator implements ConstraintValidator<IsValidDiagnosisType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(DiagnosisType.PRIMARY.value())
					&& !arg0.equalsIgnoreCase(DiagnosisType.SECONDARY.value())) {
				return false;
			}
		}
		return true;
	}

}
