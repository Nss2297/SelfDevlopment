package com.waseel.brservice.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;

@Service
public class MapperService {

	public SensitiveDrugResponseModel mapSensitiveDrugResponseModel(ContentCachingResponseWrapper response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String responseStr = new String(response.getContentAsByteArray());
			if (!StringUtils.isBlank(responseStr))
				return mapper.readValue(responseStr, SensitiveDrugResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SensitiveDrugRequestModel mapSensitiveDrugRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					SensitiveDrugRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public InvalidResponseModel mapInvalidResponseModel(ContentCachingResponseWrapper responseWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(responseWrapper.getContentAsByteArray()), InvalidResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
