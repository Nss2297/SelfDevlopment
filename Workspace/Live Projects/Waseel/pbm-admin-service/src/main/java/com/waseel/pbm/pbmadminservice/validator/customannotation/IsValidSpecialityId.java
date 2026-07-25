package com.waseel.pbm.pbmadminservice.validator.customannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.waseel.pbm.pbmadminservice.validator.customvalidator.SpecialityIdValidator;

@Documented
@Constraint(validatedBy = SpecialityIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidSpecialityId {

	String message() default "exclusionSpecialty[{specialtyId}] not found or exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
