package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.PhysicianInfo;

import java.util.Optional;

public interface PhysicianInfoRepository
        extends JpaRepository<PhysicianInfo, Long>, JpaSpecificationExecutor<PhysicianInfo> {

    PhysicianInfo findByRegistrationNumber(String licenseNumber);
}
