package com.waseel.pbm.pbmadminservice.repository.businessrules;

import com.waseel.pbm.pbmadminservice.persist.businessrules.MemberPolicyAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberPolicyAssociationRepository extends JpaRepository<MemberPolicyAssociation, Long> {

    Optional<List<MemberPolicyAssociation>> findByPolicyInformationId(Long policyInformationId);

    Optional<List<MemberPolicyAssociation>> findByPolicyClassId(Long policyClassId);

    Optional<List<MemberPolicyAssociation>> findByPolicyClassIdAndPolicyInformationId(Long policyClassId,
                                                                                      Long policyInformationId);

    Optional<MemberPolicyAssociation> findByPolicyInformationIdAndMemberProfileIdAndPolicyClassId(
            Long policyInfoId, Long memberProfileId, Long policyClassId);

    @Query(value = "SELECT MAX(\"MEMBER_POLICY_ASSOCIATION_ID\") from \"MEMBER_POLICY_ASSOCIATION\"", nativeQuery = true)
    Long findLatestId();
}
