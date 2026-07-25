package com.waseel.pbmschedulerservice.repository.businessrules;

import com.waseel.pbmschedulerservice.persist.businessrules.MemberPolicyAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPolicyAssociationRepository extends JpaRepository<MemberPolicyAssociation, Long> {

    Optional<MemberPolicyAssociation> findByMemberIdAndMemberProfileIdAndPolicyInformationId(
            String memberId, Long memberProfileId, Long policyInformationId);
}
