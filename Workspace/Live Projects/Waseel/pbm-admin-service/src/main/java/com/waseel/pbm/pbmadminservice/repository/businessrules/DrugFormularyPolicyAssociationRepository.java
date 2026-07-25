package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyPolicyAssociation;

public interface DrugFormularyPolicyAssociationRepository extends JpaRepository<DrugFormularyPolicyAssociation, Long> {

	List<DrugFormularyPolicyAssociation> findByFormularyId(Long formularyId);

	Optional<DrugFormularyPolicyAssociation> findByDrugFormularyAssociationIdAndIsEnabled(
			Long drugFormularyAssociationId, boolean isEnabled);

	Optional<DrugFormularyPolicyAssociation> findByPolicyInformationIdAndAndPolicyClassIdAndMemberPolicyAssociationIdAndIsEnabled(
			Long policyInfoId, Long policyClassId, Long memberPolicyAssociationId,
			boolean isEnabled);

	Optional<DrugFormularyPolicyAssociation> findByPolicyInformationIdAndPolicyClassIdAndIsEnabledAndFormularyId(
			Long policyInformationId, Long policyClassId, boolean isEnabled, Long formularyId);
}
