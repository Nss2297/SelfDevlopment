package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;

public interface DrugFormularyMetadataRepository extends JpaRepository<DrugFormularyMetadata, Long> {

	Optional<DrugFormularyMetadata> findByFormularyIdAndPayerIdAndIsDeleted(Long formularyId, String payerId,
			boolean isDeleted);

	Optional<DrugFormularyMetadata> findByFormularyName(String name);
	
	Optional<DrugFormularyMetadata> findByFormularyNameIgnoreCaseAndIsDeleted(String name, boolean isDeleted);
}
