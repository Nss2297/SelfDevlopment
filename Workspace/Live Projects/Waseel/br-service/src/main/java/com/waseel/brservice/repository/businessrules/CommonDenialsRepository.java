package com.waseel.brservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.brservice.persist.businessrules.CommonDenials;

public interface CommonDenialsRepository extends JpaRepository<CommonDenials, Long>{
	
	 Optional<CommonDenials> findByDenialCode(String denialCode);
}
