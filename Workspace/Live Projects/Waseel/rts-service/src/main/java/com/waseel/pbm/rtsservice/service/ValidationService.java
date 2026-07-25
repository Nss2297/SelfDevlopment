package com.waseel.pbm.rtsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.pbm.rtsservice.enums.ScreeningModules;
import com.waseel.pbm.rtsservice.exceptions.RTSException;
import com.waseel.pbm.rtsservice.model.DrugList;
import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;
import com.waseel.pbm.rtsservice.persist.mdss.PayerModuleConfiguration;
import com.waseel.pbm.rtsservice.repository.mdss.PayerModuleConfigurationRepository;

@Service
public class ValidationService {

	@Autowired
	private PayerModuleConfigurationRepository modulesConfigurationRepo;

	public void validate(RTSRequest rtsRequest)throws RTSException {
		daysOfSupplyValidation(rtsRequest);
	}

	private void daysOfSupplyValidation(RTSRequest rtsRequest) throws RTSException {
		PayerModuleConfiguration payerModuleConfiguration = modulesConfigurationRepo
				.findByPayerIdAndModuleId(rtsRequest.getPayerId(), ScreeningModules.RTS.value().doubleValue());
		if (payerModuleConfiguration != null && payerModuleConfiguration.getIsEnabled().equalsIgnoreCase("1")) {
			for (DrugList drug : rtsRequest.getDrugList()) {
				if (StringUtils.isBlank(drug.getDaysOfSupply())) {
					throw new RTSException(
							populateInvalidRtsResponse(rtsRequest, "DaysOfSupply : Should not be Null or Empty."));
				}
			}
		}
	}

	private RTSResponse populateInvalidRtsResponse(RTSRequest rtsRequest, String errorMessage) {
		RTSResponse invalidRtsResponse = new RTSResponse();
		invalidRtsResponse.setRequestId(rtsRequest.getRequestId());
		invalidRtsResponse.setStatus("Invalid");
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		invalidRtsResponse.setErrors(errors);
		invalidRtsResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidRtsResponse.setHttpStatusDescription(errors.toString());
		return invalidRtsResponse;
	}
}
