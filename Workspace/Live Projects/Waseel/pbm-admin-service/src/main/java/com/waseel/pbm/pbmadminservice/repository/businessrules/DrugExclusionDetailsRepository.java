package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;

@Repository
public interface DrugExclusionDetailsRepository extends JpaRepository<DrugExclusionDetails, Long> {

	List<DrugExclusionDetails> findByExclusionIdAndIsDeleted(Long exclusionId, boolean isDeleted);

	@Query(nativeQuery = true, value = "SELECT MAX(DRUG_EXCLUSION_DETAILS_ID)+1 FROM PBM_BUSINESS_RULES.DRUG_EXCLUSION_DETAILS")
	Long fetchPrimaryKeyForDrugExclusionDetails();

	Optional<DrugExclusionDetails> findByExclusionIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(Long exclusionId,
			Long waseelDrugId, String drugCode, boolean isDeleted);

	Optional<DrugExclusionDetails> findByDrugExclusionDetailsIdAndIsDeleted(Long drugExclusionDetailsId,
			boolean isDeleted);

	Optional<List<DrugExclusionDetails>> findByExclusionId(Long exclusionId);

}
