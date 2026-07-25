package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.enums.ServiceStatus;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidServiceStatus;

public class IsValidServiceStatusValidator implements ConstraintValidator<IsValidServiceStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {

		if (value == null || value.isEmpty() || value.trim().getBytes().length > 50) {
			return false;
		}
		return value.equalsIgnoreCase(ServiceStatus.APPROVED.value())
				|| value.equalsIgnoreCase(ServiceStatus.REJECTED.value());
	}
}
