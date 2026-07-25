package com.waseel.policy.repository.businessrules;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.ClassBenefit;

public interface ClassBenefitRepository extends CrudRepository<ClassBenefit, Long> {

	Optional<ClassBenefit> findByBenefitCodeAndPolicyClass_PolicyClassId(String benefitCode, long policyClassId);

}
