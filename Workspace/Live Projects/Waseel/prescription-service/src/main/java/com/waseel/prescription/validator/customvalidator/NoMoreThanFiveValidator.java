package com.waseel.prescription.validator.customvalidator;

import com.waseel.prescription.validator.customannotation.NoMoreThanFiveLength;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoMoreThanFiveValidator implements ConstraintValidator<NoMoreThanFiveLength, String> {

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        return StringUtils.isBlank(arg0) || arg0.trim().getBytes().length <= 5;
    }
}
