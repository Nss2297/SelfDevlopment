package com.waseel.drugformulary.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.drugformulary.model.MemberPolicyAssociationProjection;
import com.waseel.drugformulary.persist.businessrules.MemberPolicyAssociation;

public interface MemberPolicyAssociationRepository extends JpaRepository<MemberPolicyAssociation, Long> {

	@Query("SELECT mpa FROM MemberProfile mp, MemberPolicyAssociation mpa, PolicyInformation pi"
			+ " WHERE mpa.memberProfileId = mp.memberProfileId"
			+ " AND mpa.policyInformationId = pi.policyInformationId"
			+ " AND mp.idNumber = :idNumber"
			+ " AND pi.payerId = :payerId")
	Optional<MemberPolicyAssociation> findByIdNumber(@Param("idNumber") Long idNumber,
			@Param("payerId") String payerId);

	@Query(value = "SELECT fm.FORMULARY_ID as \"formularyId\", pi.POLICY_INFORMATION_ID  as \"policyInformationId\", pc.POLICY_CLASS_ID  as \"policyClassId\", mpa.MEMBER_POLICY_ASSOCIATION_ID  as \"memberPolicyAssociationId\" " + 
				"from PBM_BUSINESS_RULES.drug_formulary_policy_association dfpa " + 
				"JOIN PBM_BUSINESS_RULES.DRUG_FORMULARY_METADATA fm ON dfpa.FORMULARY_ID = fm.FORMULARY_ID " + 
				"JOIN PBM_BUSINESS_RULES.POLICY_INFORMATION pi ON dfpa.POLICY_INFORMATION_ID = pi.POLICY_INFORMATION_ID " + 
				"LEFT JOIN PBM_BUSINESS_RULES.POLICY_CLASSES pc ON dfpa.POLICY_CLASS_ID = pc.POLICY_CLASS_ID  " + 
				"LEFT JOIN PBM_BUSINESS_RULES.MEMBER_POLICY_ASSOCIATION mpa ON dfpa.MEMBER_POLICY_ASSOCIATION_ID = mpa.MEMBER_POLICY_ASSOCIATION_ID " + 
			"WHERE  " + 
				"fm.is_deleted = 0 " + 
				"AND dfpa.is_enabled = 1 " + 
				"AND pi.POLICY_NUMBER = :policyNumber  " + 
				"AND (pc.CLASS_CODE is null or pc.CLASS_CODE = :classCode)  " + 
				"AND (mpa.MEMBER_ID is null or mpa.MEMBER_ID = :memberId) " + 
				"ORDER BY pc.CLASS_CODE nulls last, mpa.MEMBER_ID nulls last " + 
			"FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Optional<MemberPolicyAssociationProjection> findByPolicyNumberAndClassCodeAndMemberId(String policyNumber, String classCode, String memberId);

}
