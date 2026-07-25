package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionMetadata;

public interface DrugExclusionMetadataRepository extends JpaRepository<DrugExclusionMetadata, Long> {

	DrugExclusionMetadata findByExclusionIdAndPayerIdAndIsDeleted(Long exclusionId, Long payerId, boolean isDeleted);

	List<DrugExclusionMetadata> findByExclusionIdInAndPayerIdAndIsDeleted(List<Long> exclusionIds, Long payerId,
			boolean isDeleted);

}
