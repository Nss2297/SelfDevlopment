package com.waseel.pbm.payercustomizationservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;

@Repository
public interface CustomizationRequestMetadataRepository extends CrudRepository<CustomizationRequestMetadata, Long> {

	Optional<CustomizationRequestMetadata> findByDrugCode(String drugCode);

	Optional<CustomizationRequestMetadata> findByPayerIdAndDrugCodeAndIsDeletedAndStatus(String payerId,
			String drugCode, boolean isDeleted, String status);

	Optional<CustomizationRequestMetadata> findByCustomizationRequestsIdAndIsDeleted(Long customizationRequestsId,
			boolean isDeleted);
}
