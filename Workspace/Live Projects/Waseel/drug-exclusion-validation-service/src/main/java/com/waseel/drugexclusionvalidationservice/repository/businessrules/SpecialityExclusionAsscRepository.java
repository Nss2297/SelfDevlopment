package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.SpecialityExclusionAssc;

public interface SpecialityExclusionAsscRepository extends JpaRepository<SpecialityExclusionAssc, Long> {

	List<SpecialityExclusionAssc> findBySpecialityIdAndIsEnabled(BigDecimal specialityId, boolean isEnabled);
}
