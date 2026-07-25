package com.waseel.prescription.validator.customvalidator;


import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.validator.customannotation.IsValidGender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsValidGenderValidator implements ConstraintValidator<IsValidGender, String> {

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        if (arg0 == null) {
            return false;
        } else if (arg0.isEmpty()) {
            return false;
        } else {
            if (!arg0.equalsIgnoreCase(GenderType.FEMALE.toString()) && !arg0.equalsIgnoreCase(GenderType.MALE.toString())) {
                return false;
            }
        }
        return true;
    }

}
