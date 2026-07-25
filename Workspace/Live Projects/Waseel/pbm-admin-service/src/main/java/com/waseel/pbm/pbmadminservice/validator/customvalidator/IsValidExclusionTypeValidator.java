package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidExclusionType;

public class IsValidExclusionTypeValidator implements ConstraintValidator<IsValidExclusionType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) {
			return false;
		} else if (arg0.isEmpty()) {
			return false;
		} else {
			if (!arg0.equalsIgnoreCase(ExclusionType.HIGH_COST_EXCLUSION.value())
					&& !arg0.equalsIgnoreCase(ExclusionType.NETWORK_EXCLUSION.value())
					&& !arg0.equalsIgnoreCase(ExclusionType.PROVIDER_EXCLUSION.value())
					&& !arg0.equalsIgnoreCase(ExclusionType.SPECIALITY_EXCLUSION.value())) {
				return false;
			}
		}
		return true;
	}

}
