package com.waseel.pbm.dssservice.service.managementservice;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.DssResponse;

@Service
public class RestApiLoggingService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MapperService utilsService;

	public void beforeRequest(ContentCachingRequestWrapper request) {
		logRequestHeader(request, request.getRemoteAddr() + "|>");
	}
	
	public void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		logRequestBody(request);
		logResponse(response, request.getRemoteAddr() + "<|");
	}
	
	private void logRequestBody(ContentCachingRequestWrapper request) {
		String requestBody = new String(request.getContentAsByteArray()).replace("\\r\\n", "").replace("\\s", "");
		log.info("Request: {} ", requestBody);
	}
	
	private void logRequestHeader(ContentCachingRequestWrapper request, String prefix) {
		String queryString = request.getQueryString();
		if (queryString == null) {
			log.info("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
		} else {
			log.info("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
		}
		Collections.list(request.getHeaderNames())
				.forEach(headerName -> Collections.list(request.getHeaders(headerName))
						.forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue)));
		log.info("{}", prefix);
	}
	
	private void logResponse(ContentCachingResponseWrapper response, String prefix) {
		int status = response.getStatus();
		DssResponse dssRes = utilsService.mapDssResponse(response);
		CancelOverrideResponse cancelRes = utilsService.mapCancellationResponse(response);

		log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
		response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName)
				.forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue)));
		log.info("{}", prefix);
		
		String responseBody;
		if(dssRes != null) {
			dssRes.setTransactionLogId(null);
			responseBody =	utilsService.mapString(dssRes);
		}else if(cancelRes != null) {
			cancelRes.setTransactionLogId(null);
			responseBody = utilsService.mapString(cancelRes);
		}else {
			responseBody = new String(response.getContentAsByteArray());
		}
		log.info("Response: {} ", responseBody);
	}
}
