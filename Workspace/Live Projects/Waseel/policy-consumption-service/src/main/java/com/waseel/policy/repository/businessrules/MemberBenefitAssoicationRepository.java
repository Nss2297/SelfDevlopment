package com.waseel.policy.repository.businessrules;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.MemberBenefitAssoication;

public interface MemberBenefitAssoicationRepository extends CrudRepository<MemberBenefitAssoication, Long> {

	Optional<MemberBenefitAssoication> findByClassBenefit_ClassBenefitIdAndMemberPolicyAssociation_MemberPolicyAssociationId(
			long classBenefitId, long memberPolicyAssociationId);

}
