package com.waseel.smsservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.smsservice.enums.DenialCode;
import com.waseel.smsservice.enums.DenialDescription;
import com.waseel.smsservice.model.UnifonicResponseModel;

import feign.FeignException;

@Service
public class InvalidResponseService {
	private final Logger log = LoggerFactory.getLogger(InvalidResponseService.class);

	public UnifonicResponseModel populateInternalServerErrorResponse() {
		return new UnifonicResponseModel(false, "Error", DenialDescription.INTERNAL_SERVER_ERROR.value(),
				DenialCode.INTERNAL_SERVER_ERROR.value(), null);
	}

	public UnifonicResponseModel populateUnconfiguredAppResponse() {
		return new UnifonicResponseModel(false, "Unconfigured Application", DenialDescription.UNCONFIGURED_APP.value(),
				DenialCode.UNCONFIGURED_OR_DISABLED_APP.value(), null);
	}

	public UnifonicResponseModel populateSmsInvalidFailedResponse(FeignException ex) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(ex.contentUTF8(), UnifonicResponseModel.class);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public UnifonicResponseModel populateSmsInvalidRequest() {
		return new UnifonicResponseModel(false, "Invalid request body", DenialDescription.INVALID_REQUEST_BODY.value(),
				DenialCode.INVALID_REQUEST_BODY.value(), null);
	}

	public UnifonicResponseModel populateSmsNotAllowedPhoneNumber(String phoneNumber) {
		return new UnifonicResponseModel(false, "This Phone Number is Not Allowed [" + phoneNumber + "]",
				DenialDescription.INVALID_REQUEST_BODY.value(), DenialCode.INVALID_REQUEST_BODY.value(), null);
	}

	public UnifonicResponseModel getMethodArgumentNotValidResponse(List<String> errors) {
		return new UnifonicResponseModel(false, errors.toString(), DenialDescription.INVALID_REQUEST_BODY.value(),
				DenialCode.INVALID_REQUEST_BODY.value(), null);
	}

	public UnifonicResponseModel populateSmsNoResponse() {
		return new UnifonicResponseModel(false, "No response returned.", DenialDescription.NO_RESPONSE.value(),
				DenialCode.NO_SMS_RESPONSE.value(), null);
	}
}
