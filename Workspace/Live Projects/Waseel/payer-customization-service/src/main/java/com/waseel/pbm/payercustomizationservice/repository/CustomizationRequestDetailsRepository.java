package com.waseel.pbm.payercustomizationservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestDetail;

@Repository
public interface CustomizationRequestDetailsRepository extends CrudRepository<CustomizationRequestDetail, Long> {

	Optional<CustomizationRequestDetail> findByCustomizationRequestsIdAndCustomizationKey(Long customizationRequestId,
			String key);
}
