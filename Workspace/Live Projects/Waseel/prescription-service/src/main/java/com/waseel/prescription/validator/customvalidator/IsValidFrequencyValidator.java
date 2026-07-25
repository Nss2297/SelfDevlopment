package com.waseel.prescription.validator.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.validator.customannotation.IsValidFrequencyType;

public class IsValidFrequencyValidator implements ConstraintValidator<IsValidFrequencyType, String> {

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(arg0)) {
			return false;
		} else {
			if (!StringUtils.equals(arg0, FrequencyType.AS_NEEDED.value())
					&& !StringUtils.equals(arg0, FrequencyType.AT_BED_TIME.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_12_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_24_HOUR.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_2_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_3_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_4_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_6_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.EVERY_8_HOURS.value())
					&& !StringUtils.equals(arg0, FrequencyType.ONCE_A_WEEK.value())
					&& !StringUtils.equals(arg0, FrequencyType.ONCE_DAILY.value())
					&& !StringUtils.equals(arg0, FrequencyType.OTHERS.value())
					&& !StringUtils.equals(arg0, FrequencyType.TWICE_DAILY.value())) {
				return false;
			}
		}
		return true;
	}

}
