package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.EPrescriptionStatusType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidEPrescriptionStatus;

public class IsValidEPrescriptionStatusValidator implements ConstraintValidator<IsValidEPrescriptionStatus, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(EPrescriptionStatusType.APPROVED.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.REJECTED.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.DISPENSED.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.PARTIAL_APPROVED.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.PENDING.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.CANCELLED.value())
					&& !arg0.equalsIgnoreCase(EPrescriptionStatusType.PARTIAL_DISPENSED.value())) {
				return false;
			}
		}
		return true;
	}

}
