package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.RequestType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidRequestType;

public class IsValidRequestTypeValidator implements ConstraintValidator<IsValidRequestType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(RequestType.NEW.value()) && !arg0.equalsIgnoreCase(RequestType.FOLLOWUP.value())
					&& !arg0.equalsIgnoreCase(RequestType.CANCELLATION.value())
					&& !arg0.equalsIgnoreCase(RequestType.DISPENSED.value())) {
				return false;
			}
		}
		return true;
	}

}
