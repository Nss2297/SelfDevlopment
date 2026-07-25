package com.waseel.policy.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.policy.persist.businessrules.CommonDenial;

public interface CommonDenialsRepository extends JpaRepository<CommonDenial, Long> {

	Optional<CommonDenial> findByDenialCode(String denialCode);
}
