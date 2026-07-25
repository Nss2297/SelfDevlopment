package com.waseel.prescription.validator.customvalidator;

import com.waseel.prescription.validator.customannotation.IsValidDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
