package com.waseel.pbm.fdbvalidationservice.service.manpulationservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;

@Service
public class DataPopulationService {

	public DssResponse populateInvalidResponse(String requestId, List<String> errorMessages) {
		DssResponse invalidFdbResponse = new DssResponse();
		invalidFdbResponse.setRequestId(requestId);
		invalidFdbResponse.setStatus("Invalid");
		invalidFdbResponse.setErrors(errorMessages);
		invalidFdbResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidFdbResponse.setHttpStatusDescription(errorMessages.toString());
		return invalidFdbResponse;
	}

	public DssResponse populateFailedResponse(ContentCachingRequestWrapper request) {		
		DssResponse failedFdbResponse = new DssResponse();
		failedFdbResponse.setRequestId(getRequestId(request));
		failedFdbResponse.setStatus("Failed");
		failedFdbResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		failedFdbResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return failedFdbResponse;
	}

	private String getRequestId(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		DssRequest dssReq = null;
		try {
			dssReq = mapper.readValue(new String(request.getContentAsByteArray()), DssRequest.class);
			return dssReq.getRequestId();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
