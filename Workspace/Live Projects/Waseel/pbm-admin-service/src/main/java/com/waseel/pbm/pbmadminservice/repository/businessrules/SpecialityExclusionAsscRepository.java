package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.SpecialityExclusionAssc;

public interface SpecialityExclusionAsscRepository extends JpaRepository<SpecialityExclusionAssc, Long> {

	Optional<SpecialityExclusionAssc> findBySpecialityExclusionAsscIdAndIsEnabled(Long specialityExclusionAsscId,
			Boolean isEnabled);

	Optional<SpecialityExclusionAssc> findByExclusionIdAndSpecialityId(Long exclusionId, BigDecimal specialityId);

	Optional<List<SpecialityExclusionAssc>> findByExclusionId(Long exclusionId);
}