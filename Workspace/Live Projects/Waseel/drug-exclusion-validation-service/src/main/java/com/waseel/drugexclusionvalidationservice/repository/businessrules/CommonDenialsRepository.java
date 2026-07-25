package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.CommonDenials;

import java.util.Optional;

public interface CommonDenialsRepository extends JpaRepository<CommonDenials, Long> {

    Optional<CommonDenials> findByDenialCode(String denialCode);
}
