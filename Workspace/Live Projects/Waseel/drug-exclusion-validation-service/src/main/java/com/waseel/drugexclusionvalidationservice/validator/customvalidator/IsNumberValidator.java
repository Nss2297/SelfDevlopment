package com.waseel.drugexclusionvalidationservice.validator.customvalidator;

import org.apache.commons.lang.StringUtils;

import com.waseel.drugexclusionvalidationservice.validator.customannotation.IsNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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