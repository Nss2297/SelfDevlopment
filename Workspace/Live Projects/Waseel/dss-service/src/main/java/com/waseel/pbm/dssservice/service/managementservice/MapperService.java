package com.waseel.pbm.dssservice.service.managementservice;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;

@Service
public class MapperService {

	public DssResponse mapDssResponse(ContentCachingResponseWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		DssResponse dssResponse = null;
		try {
			dssResponse = mapper.readValue(new String(request.getContentAsByteArray()), DssResponse.class);
			return dssResponse;
		} catch (Exception e) {
			return dssResponse;
		}
	}
	
	public String mapString(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		String dssResponse = null;
		try {
			dssResponse = mapper.convertValue(new Gson().toJson(response), String.class);
			return dssResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return dssResponse;
		}
	}
	
	public DssRequest mapDssRequest(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		DssRequest dssRequest = null;
		try {
			dssRequest = mapper.readValue(new String(request.getContentAsByteArray()), DssRequest.class);
			return dssRequest;
		} catch (Exception e) {
			return dssRequest;
		}
	}
	
	public CancellationOverrideRequest mapCancellationRequest(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		CancellationOverrideRequest cancelRequest = null;
		try {
			cancelRequest = mapper.readValue(new String(request.getContentAsByteArray()), CancellationOverrideRequest.class);
			return cancelRequest;
		} catch (Exception e) {
			return cancelRequest;
		}
	}

	
	public CancelOverrideResponse mapCancellationResponse(ContentCachingResponseWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		CancelOverrideResponse cancelResponse = null;
		try {
			cancelResponse = mapper.readValue(new String(request.getContentAsByteArray()), CancelOverrideResponse.class);
			return cancelResponse;
		} catch (Exception e) {
			return cancelResponse;
		}
	}
	
	public DssResponse mapDssResponse(String response) {
		ObjectMapper mapper = new ObjectMapper();
		DssResponse result = null;
		try {
			return mapper.readValue(response, DssResponse.class);
		} catch (Exception e) {
			return result;
		}
	}
}
