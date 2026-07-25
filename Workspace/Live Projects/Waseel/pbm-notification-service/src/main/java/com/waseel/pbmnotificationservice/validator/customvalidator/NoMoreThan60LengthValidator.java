package com.waseel.pbmnotificationservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbmnotificationservice.validator.customannotation.NoMoreThan60Length;

public class NoMoreThan60LengthValidator implements ConstraintValidator<NoMoreThan60Length, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 60;
    }
}
