package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan150Length;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoMoreThan150LengthValidator implements ConstraintValidator<NoMoreThan150Length, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.trim().getBytes().length <= 150;
    }
}
