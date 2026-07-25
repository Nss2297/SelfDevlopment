package com.waseel.brservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.brservice.validator.customannotation.IsNumber;

public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        try {

            if (StringUtils.isNotBlank(arg0)) {
                if (arg0.contains(".") || arg0.contains(",")) {
                    Double.parseDouble(arg0);
                } else {
                    Long.parseLong(arg0);
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}