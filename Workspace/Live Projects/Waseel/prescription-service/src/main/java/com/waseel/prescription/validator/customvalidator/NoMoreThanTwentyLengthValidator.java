package com.waseel.prescription.validator.customvalidator;

import com.waseel.prescription.validator.customannotation.NoMoreThanTwentyLength;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoMoreThanTwentyLengthValidator implements ConstraintValidator<NoMoreThanTwentyLength, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 20;
    }
}
