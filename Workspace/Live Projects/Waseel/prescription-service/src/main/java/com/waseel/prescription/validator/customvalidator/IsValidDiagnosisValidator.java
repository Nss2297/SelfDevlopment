package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.model.enums.DiagnosisType;
import com.waseel.prescription.validator.customannotation.IsValidDiagnosisType;

public class IsValidDiagnosisValidator implements ConstraintValidator<IsValidDiagnosisType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(arg0)) {
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
