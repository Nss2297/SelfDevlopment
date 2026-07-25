package com.waseel.prescription.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.waseel.prescription.persist.businessrules.PhysicianInfo;

public interface PhysicianInfoRepository
        extends JpaRepository<PhysicianInfo, Long>, JpaSpecificationExecutor<PhysicianInfo> {

	Optional<PhysicianInfo> findByRegistrationNumber(String registrationNumber);
	
	Optional<PhysicianInfo> findByRegistrationNumberAndProviderId(String registrationNumber, Long providerId);
}
