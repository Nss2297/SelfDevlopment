package com.waseel.policy.repository.businessrules;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.BenefitCas;

public interface BenefitCaseRepository extends CrudRepository<BenefitCas, Long> {

	Optional<BenefitCas> findByClassBenefit_ClassBenefitIdAndCaseCode(long classBenefitId, String benefitCase);

}
