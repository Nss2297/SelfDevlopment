package com.waseel.pbm.dssservice.validator.customvalidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.dssservice.validator.customannotation.IsValidDateFormat;

public class IsValidDateFormatValidator implements ConstraintValidator<IsValidDateFormat, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {

		if (arg0 != null) {
			if (arg0.trim().getBytes().length > 10) {
				return false;
			}
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate.parse(arg0, formatter);
			} catch (DateTimeParseException e) {
				return false;
			}
		}
		return true;
	}
}
