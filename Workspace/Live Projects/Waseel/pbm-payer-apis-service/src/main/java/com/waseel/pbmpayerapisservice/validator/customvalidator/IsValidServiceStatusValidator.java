package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.ServiceStatus;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidServiceStatus;

public class IsValidServiceStatusValidator implements ConstraintValidator<IsValidServiceStatus, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(ServiceStatus.APPROVED.value())
					&& !arg0.equalsIgnoreCase(ServiceStatus.REJECTED.value())
					&& !arg0.equalsIgnoreCase(ServiceStatus.DISPENSED.value())
					&& !arg0.equalsIgnoreCase(ServiceStatus.PENDING.value())) {
				return false;
			}
		}
		return true;
	}

}
