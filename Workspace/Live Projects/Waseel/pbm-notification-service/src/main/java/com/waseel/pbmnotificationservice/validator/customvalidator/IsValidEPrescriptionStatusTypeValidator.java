package com.waseel.pbmnotificationservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmnotificationservice.model.enums.EPrescriptionStatusType;
import com.waseel.pbmnotificationservice.validator.customannotation.IsValidEPrescriptionStatusType;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

public class IsValidEPrescriptionStatusTypeValidator
		implements ConstraintValidator<IsValidEPrescriptionStatusType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		return !StringUtils.isBlank(value) && (value.equalsIgnoreCase(EPrescriptionStatusType.APPROVED.value())
				|| value.equalsIgnoreCase(EPrescriptionStatusType.REJECTED.value())
				|| value.equalsIgnoreCase(EPrescriptionStatusType.PARTIAL_APPROVED.value())
				|| value.equalsIgnoreCase(EPrescriptionStatusType.DISPENSED.value())
				|| value.equalsIgnoreCase(EPrescriptionStatusType.PARTIAL_DISPENSED.value()));
	}
}
