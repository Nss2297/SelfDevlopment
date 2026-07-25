package com.waseel.policy.service.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.policy.model.CancellAndDispensePolicyRequestModel;
import com.waseel.policy.model.DeactivatePrescriptionRequestModel;
import com.waseel.policy.model.DispensibleDrugsRequestModel;
import com.waseel.policy.model.PolicyRequestModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.model.client.InvalidResponseModel;

@Service
public class MapperService {

	private static final Logger log = LoggerFactory.getLogger(MapperService.class);

	public PolicyRequestModel mapPolicyRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()), PolicyRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}

	public PolicyResponseModel mapPolicyResponseModel(ContentCachingResponseWrapper responseWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(responseWrapper.getContentAsByteArray()), PolicyResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}

	public InvalidResponseModel mapPbmPayerApisService(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, InvalidResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}

	public CancellAndDispensePolicyRequestModel mapCancellAndDispensePolicyRequestModel(
			ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					CancellAndDispensePolicyRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}

	public DeactivatePrescriptionRequestModel mapDeactivatePrescriptionRequestModel(
			ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					DeactivatePrescriptionRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}

	public DispensibleDrugsRequestModel mapDispensableDrugsRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					DispensibleDrugsRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		}
	}
}