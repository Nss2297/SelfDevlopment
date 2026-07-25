package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.ClassBenefits;

public interface ClassBenefitsRepository extends JpaRepository<ClassBenefits, Long> {

	Optional<ClassBenefits> findByPolicyClassIdAndBenefitCode(Long policyClassId,String benefitCode);
}
