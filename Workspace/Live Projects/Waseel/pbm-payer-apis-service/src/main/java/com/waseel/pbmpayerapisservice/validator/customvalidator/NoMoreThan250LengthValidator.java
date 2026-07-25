package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan250Length;

public class NoMoreThan250LengthValidator implements ConstraintValidator<NoMoreThan250Length, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 250;
    }
}
