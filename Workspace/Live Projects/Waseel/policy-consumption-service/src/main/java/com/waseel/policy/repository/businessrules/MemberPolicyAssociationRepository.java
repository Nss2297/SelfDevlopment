package com.waseel.policy.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.policy.persist.businessrules.MemberPolicyAssociation;

@Repository
public interface MemberPolicyAssociationRepository extends CrudRepository<MemberPolicyAssociation, Long> {

	Optional<List<MemberPolicyAssociation>> findByMemberProfile_MemberProfileId(long memberProfileId);

	Optional<MemberPolicyAssociation> findByMemberProfile_MemberProfileIdAndPolicyInformation_PayerId(
			long memberProfileId, String payerId);
}
