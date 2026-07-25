package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NotContainsWhiteSpace;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotContainsWhiteSpaceValidator implements ConstraintValidator<NotContainsWhiteSpace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || !value.contains(" ");
    }
}
