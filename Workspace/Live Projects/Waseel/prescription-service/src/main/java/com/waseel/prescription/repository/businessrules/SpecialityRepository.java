package com.waseel.prescription.repository.businessrules;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.prescription.persist.businessrules.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, BigDecimal> {

	Optional<Speciality> findBySpecialityNameAndIsDeleted(String specialityName, Boolean isDeleted);
}
