package com.waseel.drugexclusionvalidationservice.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;

@Service
public class MapperService {

	public DrugExclusionResponseModel mapSpecialityExclusionResponseModel(ContentCachingResponseWrapper response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String responseStr = new String(response.getContentAsByteArray());
			if(!StringUtils.isBlank(responseStr))
				return mapper.readValue(responseStr, DrugExclusionResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DrugExclusionRequestModel mapSpecialityExclusionRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					DrugExclusionRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
