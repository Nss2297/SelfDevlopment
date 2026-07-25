package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, BigDecimal> {

	Speciality findBySpecialityNameAndIsDeleted(String physicianSpeciality, boolean isDeleted);
}
