package com.waseel.prescription.repository.mdss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.mdss.CustomizationRequestMetadata;

@Repository
public interface CustomizationRequestMetadataRepository extends JpaRepository<CustomizationRequestMetadata, Long> {

}
