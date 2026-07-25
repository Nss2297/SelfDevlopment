package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.MemberChronicDiagnosisAssoc;

@Repository
public interface MemberChronicDiagnosisAssocRepository extends CrudRepository<MemberChronicDiagnosisAssoc, Double> {
	MemberChronicDiagnosisAssoc findByMemberCroDiagnosisAssocIdAndDiagnosisCode(Integer memberCroDiagnosisAssocId,
			String diagnosisCode);

	@Query("select max(memberCroDiagnosisAssocId) from MemberChronicDiagnosisAssoc model ")
	Integer findMaxMemberCroDiagnosisAssocId();
	// <optional>MemberChronicDiagnosisAssoc
	// findFirstOrderByMemberCroDiagnosisAssocIdDesc();

}