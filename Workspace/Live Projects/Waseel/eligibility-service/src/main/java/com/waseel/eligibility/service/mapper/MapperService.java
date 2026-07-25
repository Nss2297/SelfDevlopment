package com.waseel.eligibility.service.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.eligibility.model.EligibilityResponseModel;

@Service
public class MapperService {

	private final Logger log = LoggerFactory.getLogger(MapperService.class);

	public EligibilityResponseModel mapEligibilityResponse(ContentCachingResponseWrapper response) {
		ObjectMapper mapper = new ObjectMapper();
		EligibilityResponseModel eligibilityResponseModel = null;
		try {
			eligibilityResponseModel = mapper.readValue(new String(response.getContentAsByteArray()),
					EligibilityResponseModel.class);
		} catch (Exception e) {
			log.error("", e);
		}
		return eligibilityResponseModel;
	}
}
