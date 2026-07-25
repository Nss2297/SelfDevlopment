package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.DeptSpecPhyscAssc;

import java.math.BigDecimal;

public interface DeptSpecPhyscAsscRepository extends JpaRepository<DeptSpecPhyscAssc, Long> {

	DeptSpecPhyscAssc findByPhysicianInfoIdAndSpecialityIdAndIsEnabled(Long physicianInfoId, BigDecimal specialityId,
			boolean isEnabled);
}
