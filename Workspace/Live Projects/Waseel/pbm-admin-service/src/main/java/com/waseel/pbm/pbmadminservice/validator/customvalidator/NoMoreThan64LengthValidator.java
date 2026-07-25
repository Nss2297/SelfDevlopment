package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan64Length;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoMoreThan64LengthValidator implements ConstraintValidator<NoMoreThan64Length, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 64;
    }
}
