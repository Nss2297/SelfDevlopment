package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.validator.customannotation.IsValidServiceStatus;

public class IsValidServiceStatusValidator implements ConstraintValidator<IsValidServiceStatus, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(ServiceStatus.APPROVED.name())
					&& !arg0.equalsIgnoreCase(ServiceStatus.REJECTED.name())) {
				return false;
			}
		}
		return true;
	}

}
