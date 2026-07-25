package com.waseel.pbm.rtsservice.repository.mdss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.rtsservice.persist.mdss.PayerModuleConfiguration;
import com.waseel.pbm.rtsservice.persist.mdss.PayerModuleConfigurationId;

@Repository 
public interface PayerModuleConfigurationRepository extends CrudRepository<PayerModuleConfiguration, PayerModuleConfigurationId> {

	@Query("select model from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.id.moduleId = :moduleId")
	PayerModuleConfiguration findByPayerIdAndModuleId(@Param("payerId") String payerId, @Param("moduleId") Double moduleId);

}
