package com.waseel.pbm.pbmadminservice.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.pbmadminservice.model.CommonResponse;
import com.waseel.pbm.pbmadminservice.model.drugformulary.InvalidResponseModel;

@Service
public class DataPopulationService {

	public CommonResponse populateFailedResponse() {
		CommonResponse response = new CommonResponse();
		response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setResponseDescription("Failed");
		return response;
	}

	public CommonResponse populateInvalidResponse(String response, int statusCode) {
		CommonResponse commonResponse = null;
		try {
			if (statusCode == HttpStatus.BAD_REQUEST.value() || statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()
					|| statusCode == HttpStatus.REQUEST_TIMEOUT.value()) {
				ObjectMapper mapper = new ObjectMapper();
				InvalidResponseModel invalidResponseModel = mapper.readValue(response, InvalidResponseModel.class);
				String error = StringUtils.join(invalidResponseModel.getErrors(), ',');
				commonResponse = new CommonResponse(statusCode, error);
			} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				commonResponse = new CommonResponse(statusCode, "Not able to call pbm-payer-apis service.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonResponse;
	}

	public CommonResponse populateUnAuthorizedResponse(AccessDeniedException ex) {
		CommonResponse response = new CommonResponse();
		response.setResponseCode(HttpStatus.UNAUTHORIZED.value());
		response.setResponseDescription(ex.getMessage());
		return response;
	}
}
