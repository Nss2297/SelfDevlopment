package com.waseel.drugformulary.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.drugformulary.persist.businessrules.DrugFormularyPolicyAssociation;

public interface DrugFormularyPolicyAssociationRepository extends JpaRepository<DrugFormularyPolicyAssociation, Long>{

	@Query("SELECT dfpa FROM DrugFormularyPolicyAssociation dfpa "
			+ " WHERE "
			+ " ((dfpa.formularyId IN (:formularyIds) AND dfpa.policyInformationId = :policyInformationId"
			+ " AND dfpa.policyClassId = :policyClassId AND dfpa.memberPolicyAssociationId = :memberPolicyAssociationId)"
			+ " OR"
			+ " (dfpa.formularyId IN (:formularyIds) AND dfpa.policyInformationId = :policyInformationId"
			+ " AND dfpa.policyClassId = :policyClassId)"
			+ " OR"
			+ " (dfpa.formularyId IN (:formularyIds) AND dfpa.policyInformationId = :policyInformationId))"
			+ " AND dfpa.isEnabled = true")
	Optional<DrugFormularyPolicyAssociation> getDrugFormularyPolicyAssociationDetail(
			@Param("formularyIds") List<Long> formularyIds,
			@Param("policyInformationId") Long policyInformationId,
			@Param("policyClassId") Long policyClassId,
			@Param("memberPolicyAssociationId") Long memberPolicyAssociationId);
}
