package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.waseel.pbm.pbmadminservice.persist.businessrules.Speciality;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityRepository;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidSpecialityId;

public class SpecialityIdValidator implements ConstraintValidator<IsValidSpecialityId, String> {
	private final SpecialityRepository specialityRepository;

	public SpecialityIdValidator(SpecialityRepository specialityRepository) {
		this.specialityRepository = specialityRepository;
	}

	@Override
	public boolean isValid(String specialtyId, ConstraintValidatorContext context) {
		if (specialtyId == null) {
			return true;
		}
		if (!specialtyId.matches("^\\d+(\\.\\d+)?$")) {
			return true;
		}
		try {
			Optional<Speciality> specialtyOpt = specialityRepository
					.findBySpecialityIdAndIsDeleted(new BigDecimal(specialtyId), false);
			if (!specialtyOpt.isPresent()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"exclusionSpecialty[" + specialtyId + "] not found or exists ").addConstraintViolation();
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
