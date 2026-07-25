package com.waseel.eligibility.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.eligibility.service.management.RestApiLoggingService;
import com.waseel.eligibility.service.management.TransactionLogService;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestApiLoggingService apiLoggingService;

	@Autowired
	private TransactionLogService transactionLogService;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		try {
			this.apiLoggingService.beforeRequest(requestWrapper);
			filterChain.doFilter(requestWrapper, responseWrapper);
			transactionLogService.manageTransactionLogFromResponse(request, responseWrapper, requestWrapper);
		} catch (Exception e) {
			log.error("Exception:-", e);
		} finally {
			apiLoggingService.afterRequest(requestWrapper, responseWrapper);
			responseWrapper.copyBodyToResponse();
		}
	}
}
