package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import com.waseel.pbm.pbmadminservice.validator.customannotation.AlphaNumericWithDashValidation;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphaNumericWithDashValidator implements ConstraintValidator<AlphaNumericWithDashValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.isBlank(value)) {
            Pattern p = Pattern.compile("[^(a-z0-9-)]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(value);
            return !m.find();
        }
        return true;
    }
}
