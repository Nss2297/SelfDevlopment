package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.PolicyEndorsement;

public interface PolicyEndorsementRepository extends JpaRepository<PolicyEndorsement, Long> {

	Optional<PolicyEndorsement> findByPolicyInformationIdAndEndorsementNumber(Long policyInformationId,
			String endorsementNumber);
}
