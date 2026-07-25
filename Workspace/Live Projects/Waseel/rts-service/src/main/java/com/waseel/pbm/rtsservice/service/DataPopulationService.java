package com.waseel.pbm.rtsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.rtsservice.model.RTSResponse;

@Service
public class DataPopulationService {

	@Autowired
	private MapperService mapperService;
	
	public RTSResponse populateFailedResponse(ContentCachingRequestWrapper request) {		
		RTSResponse failedFdbResponse = new RTSResponse();
		failedFdbResponse.setRequestId(mapperService.mapRTSRequest(request).getRequestId());
		failedFdbResponse.setStatus("Failed");
		failedFdbResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		failedFdbResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return failedFdbResponse;
	}
}
