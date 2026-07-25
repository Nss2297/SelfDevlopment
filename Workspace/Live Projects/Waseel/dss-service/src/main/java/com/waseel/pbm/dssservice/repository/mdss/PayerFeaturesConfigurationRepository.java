package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.dssservice.persist.mdss.PayerFeaturesConfiguration;
import com.waseel.pbm.dssservice.persist.mdss.PayerFeaturesConfigurationId;

public interface PayerFeaturesConfigurationRepository
		extends CrudRepository<PayerFeaturesConfiguration, PayerFeaturesConfigurationId> {


}
