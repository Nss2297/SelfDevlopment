package com.waseel.prescription.repository.businessrules;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.businessrules.CommonDenials;

@Repository
public interface CommonDenialsRepository extends CrudRepository<CommonDenials, Long> {
	public Optional<CommonDenials> findByDenialCode(String denialCode);
}
