package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.enums.RejectionCategory;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidRejectionCategory;

public class IsValidRejectionCategoryValidator implements ConstraintValidator<IsValidRejectionCategory, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		if (value == null || value.isEmpty() || value.trim().getBytes().length > 100) {
			return false;
		}
		return value.equalsIgnoreCase(RejectionCategory.DIAGNOSIS_CONTRAINDICATION.value())
				|| value.equalsIgnoreCase(RejectionCategory.DIAGNOSIS_INDICATION.value())
				|| value.equalsIgnoreCase(RejectionCategory.ALL.value());
	}
}
