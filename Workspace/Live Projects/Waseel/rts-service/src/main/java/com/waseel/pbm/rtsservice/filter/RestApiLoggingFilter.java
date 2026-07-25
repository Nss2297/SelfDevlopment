package com.waseel.pbm.rtsservice.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class RestApiLoggingFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
	}

	protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			beforeRequest(request, response);
			filterChain.doFilter(request, response);
		} finally {
			afterRequest(request, response);
			response.copyBodyToResponse();
		}
	}

	protected void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		logRequestHeader(request, request.getRemoteAddr() + "|>");
	}

	protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		logRequestBody(request);
		logResponse(response, request.getRemoteAddr() + "<|");
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

	private void logRequestBody(ContentCachingRequestWrapper request) {
		String requestBody = new String(request.getContentAsByteArray()).replace("\\r\\n", "");
		log.info("Request: {} ", requestBody.replace("\\s", ""));
	}

	private void logResponse(ContentCachingResponseWrapper response, String prefix) {
		int status = response.getStatus();
		log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
		response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName)
				.forEach(headerValue -> log.info("{} {}: {}", prefix, headerName, headerValue)));
		log.info("{}", prefix);
		String responseBody = new String(response.getContentAsByteArray());
		log.info("Response: {} ", responseBody);
	}

	private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			return (ContentCachingRequestWrapper) request;
		} else {
			return new ContentCachingRequestWrapper(request);
		}
	}

	private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}
}
