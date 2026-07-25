package com.waseel.pbmpayerapisservice.validator.customvalidator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbmpayerapisservice.model.enums.GenderType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidGender;


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
