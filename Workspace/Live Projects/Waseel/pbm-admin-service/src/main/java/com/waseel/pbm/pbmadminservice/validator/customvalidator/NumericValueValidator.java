package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NumericValue;

public class NumericValueValidator implements ConstraintValidator<NumericValue, String> {

	private String fieldName;

	@Override
	public void initialize(NumericValue constraintAnnotation) {
		fieldName = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)
				|| (isNumeric(value) && BigDecimal.valueOf(Double.parseDouble(value)).scale() < 3)) {
			return true;
		} else {
			if (!StringUtils.isBlank(fieldName)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(fieldName + "[" + value + "] {onlyNumericValue}")
						.addConstraintViolation();
			}
			return false;
		}
	}

	private Pattern pattern = Pattern.compile("[^0-9.]");

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return !pattern.matcher(strNum).find();
	}
}
