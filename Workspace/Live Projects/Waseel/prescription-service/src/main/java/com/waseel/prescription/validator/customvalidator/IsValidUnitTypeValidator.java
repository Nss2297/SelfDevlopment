package com.waseel.prescription.validator.customvalidator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.validator.customannotation.IsValidUnitType;


public class IsValidUnitTypeValidator implements ConstraintValidator<IsValidUnitType, String> {

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
    	  if (StringUtils.isBlank(arg0)) {
              return false;
          } else {
            if (!arg0.equalsIgnoreCase(UnitType.PACKAGE.value()) && !arg0.equalsIgnoreCase(UnitType.UNIT.value())) {
                return false;
            }
        }
        return true;
    }

}
