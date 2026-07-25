package com.waseel.pbm.pbmadminservice.validator.customannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.waseel.pbm.pbmadminservice.validator.customvalidator.NetworkIdValidator;

@Documented
@Constraint(validatedBy = NetworkIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidNetworkId {

	String message() default "exclusionNetwork[{networkId}] not found or exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
