package com.waseel.drugformulary.service;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Service
public class RestApiLoggingService {
	private final Logger log = LoggerFactory.getLogger(RestApiLoggingService.class);

	public void beforeRequest(ContentCachingRequestWrapper request) {
		logRequestHeader(request, request.getRemoteAddr() + "=>");
	}

	private void logRequestHeader(ContentCachingRequestWrapper request, String ip) {
		String queryString = request.getQueryString();
		if (null == queryString || queryString.isEmpty()) {
			log.info("{} {} {}", ip, request.getMethod(), request.getRequestURI());
		} else {
			log.info("{} {} {}?{}", ip, request.getMethod(), request.getRequestURI(), queryString);
		}
		Collections.list(request.getHeaderNames())
				.forEach(headerName -> Collections.list(request.getHeaders(headerName))
						.forEach(headerValue -> log.info("{} {}: {}", ip, headerName, headerValue)));
		log.info("{}", ip);
	}

	public void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		logRequestBody(request);
		logResponse(response, request.getRemoteAddr() + "<=");
	}

	private void logRequestBody(ContentCachingRequestWrapper request) {
		String requestBody = new String(request.getContentAsByteArray()).replace("\\r\\n", "").replace("\\s", "");
		log.info("Request: {} ", requestBody);
	}

	private void logResponse(ContentCachingResponseWrapper response, String ip) {
		int status = response.getStatus();
		log.info("{} {} {}", ip, status, HttpStatus.valueOf(status).getReasonPhrase());
		response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName)
				.forEach(headerValue -> log.info("{} {}: {}", ip, headerName, headerValue)));
		log.info("{}", ip);
		String responseBody = new String(response.getContentAsByteArray());
		log.info("Response: {} ", responseBody);
	}
}
