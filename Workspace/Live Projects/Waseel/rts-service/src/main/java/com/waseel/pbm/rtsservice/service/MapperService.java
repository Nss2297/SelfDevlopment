package com.waseel.pbm.rtsservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.rtsservice.model.RTSRequest;

@Service
public class MapperService {

	public RTSRequest mapRTSRequest(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		RTSRequest dssReq = null;
		try {
			dssReq = mapper.readValue(new String(request.getContentAsByteArray()), RTSRequest.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return dssReq;
	}
}
