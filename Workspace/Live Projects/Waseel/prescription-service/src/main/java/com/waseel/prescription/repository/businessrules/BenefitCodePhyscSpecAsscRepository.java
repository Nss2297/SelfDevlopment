package com.waseel.prescription.repository.businessrules;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;

@Repository
public interface BenefitCodePhyscSpecAsscRepository extends JpaRepository<BenefitCodePhyscSpecAssc, Long> {

	Optional<BenefitCodePhyscSpecAssc> findBySpecialityIdAndIsEnabled(BigDecimal specialityId, Boolean isEnabled);
}
