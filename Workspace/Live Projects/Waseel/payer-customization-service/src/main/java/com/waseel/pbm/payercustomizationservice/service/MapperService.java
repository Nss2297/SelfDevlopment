package com.waseel.pbm.payercustomizationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;

@Service
public class MapperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapperService.class);

	public PCRequest mapPCSRequest(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		PCRequest dssReq = null;
		try {
			dssReq = mapper.readValue(new String(request.getContentAsByteArray()), PCRequest.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return dssReq;
	}

	public CustomizationRequestModel mapCustomizationRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(requestWrapper.getContentAsByteArray(), CustomizationRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}

	public CustomizationResponseModel mapCustomizationResponseModel(
			ContentCachingResponseWrapper contentCachingResponseWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(contentCachingResponseWrapper.getContentAsByteArray()),
					CustomizationResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}
}
