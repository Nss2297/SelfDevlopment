package com.waseel.policy.repository.businessrules;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.PrescriptionMetadata;

public interface PrescriptionMetadataRepository extends CrudRepository<PrescriptionMetadata, Long> {

	Optional<PrescriptionMetadata> findByRequestId(String requestId);

}
