package com.waseel.pbmpayerapisservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbmpayerapisservice.model.enums.PhysicianCategory;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidPhysicianCategory;

public class IsValidPhysicianCategoryValidator implements ConstraintValidator<IsValidPhysicianCategory, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return (StringUtils.isBlank(arg0) || (arg0.equalsIgnoreCase(PhysicianCategory.GP.value())
				|| arg0.equalsIgnoreCase(PhysicianCategory.CONSULTANT.value())
				|| arg0.equalsIgnoreCase(PhysicianCategory.SPECIALIST.value())
				|| arg0.equalsIgnoreCase(PhysicianCategory.GENERAL_PHYSICIAN.value())));
	}

}
