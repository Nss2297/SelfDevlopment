package com.waseel.pbm.idfvalidationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.idfvalidationservice.model.DssResponse;

@Service
public class DataPopulationService {

	@Autowired
	private MapperService mapperService;
	
	public DssResponse populateFailedResponse(ContentCachingRequestWrapper request) {		
		DssResponse failedResponse = new DssResponse();
		failedResponse.setRequestId(mapperService.mapRTSRequest(request).getRequestId());
		failedResponse.setStatus("Failed");
		failedResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		failedResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return failedResponse;
	}
}
