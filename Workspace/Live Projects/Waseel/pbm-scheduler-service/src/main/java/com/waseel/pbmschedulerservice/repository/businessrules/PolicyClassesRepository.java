package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.PolicyClasses;

public interface PolicyClassesRepository extends JpaRepository<PolicyClasses, Long> {

	Optional<PolicyClasses> findByPolicyInformationIdAndClassCode(Long policyInformationId, String classCode);

}
