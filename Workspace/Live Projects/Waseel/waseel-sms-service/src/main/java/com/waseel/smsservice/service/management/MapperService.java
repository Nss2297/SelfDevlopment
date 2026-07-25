package com.waseel.smsservice.service.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;

@Service
public class MapperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapperService.class);

	public SmsRequestModel mapSmsRequestRequestModel(ContentCachingRequestWrapper contentCachingRequestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(contentCachingRequestWrapper.getContentAsByteArray(), SmsRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}

	public UnifonicResponseModel mapUnifonicResponseModel(ContentCachingResponseWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()), UnifonicResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}
}
