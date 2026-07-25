package com.waseel.prescription.specification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.common.PayerConfigModel;
import com.waseel.prescription.persist.businessrules.PayerConfiguration;
import com.waseel.prescription.repository.businessrules.PayerConfigurationRepository;

@Component
public class PayerConfigSpecification {

	@Autowired
	private PayerConfigurationRepository payerConfigurationRepository;
	
	public List<PayerConfiguration> findByPayerIdAndPayerName(String payer) {
		PayerConfigModel payerConfigModel = new PayerConfigModel(payer,payer);
		return  payerConfigurationRepository.findAll(payerConfigModel);
	}
}
