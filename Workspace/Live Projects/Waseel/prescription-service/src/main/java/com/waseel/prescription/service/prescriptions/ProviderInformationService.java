package com.waseel.prescription.service.prescriptions;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.common.ProviderInformationModel;
import com.waseel.prescription.specification.ProviderInfoSpecification;

@Service
public class ProviderInformationService {

	private final Logger log = LoggerFactory.getLogger(ProviderInformationService.class);

	@Autowired
	private ProviderInfoSpecification providerSpecification;

	public List<ProviderInformationModel> getAllProvidersInformation(String value) {
		String valueTrim = !StringUtils.isBlank(value) ? value.trim() : value;
		log.info("provider value is : {}", value);
		return providerSpecification.findByCodeOrSourceOrProviderName(valueTrim);
	}
}
