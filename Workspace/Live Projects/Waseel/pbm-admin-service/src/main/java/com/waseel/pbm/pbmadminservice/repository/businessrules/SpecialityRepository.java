package com.waseel.pbm.pbmadminservice.repository.businessrules;

import com.waseel.pbm.pbmadminservice.persist.businessrules.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, BigDecimal> {

    Optional<Speciality> findBySpecialityIdAndIsDeleted(BigDecimal specialityId, boolean isDeleted);
}
