package com.waseel.dssadminservice.validator.customvalidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.dssadminservice.validator.customannotation.IsValidDateFormat;

public class IsValidDateFormatValidator implements ConstraintValidator<IsValidDateFormat, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isNotBlank(value) && StringUtils.strip(value).getBytes().length > 10) {
			return false;
		} else {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate.parse(value, formatter);
			} catch (DateTimeParseException e) {
				return false;
			}
		}
		return true;
	}
}
