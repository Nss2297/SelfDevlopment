package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.BenefitSubcoverage;

public interface BenefitSubCoverageRepository extends JpaRepository<BenefitSubcoverage, Long> {

	Optional<BenefitSubcoverage> findByClassBenefitIdAndSubcoverageCode(Long classBenefitId, String subcoverageCode);
}
