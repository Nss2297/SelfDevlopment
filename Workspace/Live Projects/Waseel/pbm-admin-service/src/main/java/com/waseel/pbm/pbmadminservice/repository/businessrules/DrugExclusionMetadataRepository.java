package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;

public interface DrugExclusionMetadataRepository extends JpaRepository<DrugExclusionMetadata, Long> {

	Optional<DrugExclusionMetadata> findByExclusionIdAndPayerIdAndIsDeleted(Long exclusionId, Long payerId,
			boolean isDeleted);

	Optional<DrugExclusionMetadata> findByExclusionNameIgnoreCaseAndIsDeleted(String name, boolean isDeleted);

	Optional<DrugExclusionMetadata> findByExclusionNameIgnoreCase(String exclusionName);

	@Query(nativeQuery = true, value = "SELECT MAX(EXCLUSION_ID)+1 FROM PBM_BUSINESS_RULES.DRUG_EXCLUSION_METADATA")
	Long fetchPrimaryKeyForDrugExclusionMetadata();

	Optional<DrugExclusionMetadata> findByExclusionIdAndIsDeleted(Long exclusionId, boolean isDeleted);

	Optional<DrugExclusionMetadata> findByExclusionNameIgnoreCaseAndIsDeletedAndPayerId(String exclusionName,
			boolean isDeleted, Long payerId);
}
