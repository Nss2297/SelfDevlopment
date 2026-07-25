package com.waseel.pbm.payercustomizationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.PayerModuleConfiguration;
import com.waseel.pbm.payercustomizationservice.persist.PayerModuleConfigurationId;

@Repository
public interface PayerModuleConfigurationRepository
		extends CrudRepository<PayerModuleConfiguration, PayerModuleConfigurationId> {

	@Query("select model.id.moduleId from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.isEnabled = 1")
	List<Integer> findByPayerIdAndIsEnabled(@Param("payerId") String payerId);

}
