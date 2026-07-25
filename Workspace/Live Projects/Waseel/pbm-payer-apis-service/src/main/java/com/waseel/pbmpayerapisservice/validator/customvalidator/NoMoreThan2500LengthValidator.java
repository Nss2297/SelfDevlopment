package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan2500Length;

public class NoMoreThan2500LengthValidator implements ConstraintValidator<NoMoreThan2500Length, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 2500;
    }
}
