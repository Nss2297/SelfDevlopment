package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.BenefitCases;

public interface BenefitCaseRepository extends JpaRepository<BenefitCases, Long> {

	Optional<BenefitCases> findByClassBenefitIdAndCaseCode(Long classBenefitId, String caseCode);
}
