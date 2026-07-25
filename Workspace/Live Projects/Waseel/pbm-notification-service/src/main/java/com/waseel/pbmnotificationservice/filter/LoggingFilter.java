package com.waseel.pbmnotificationservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbmnotificationservice.service.EmailSmsAuditLogService;
import com.waseel.pbmnotificationservice.service.RestApiLoggingService;

@Component
@Order(2)
public class LoggingFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestApiLoggingService apiLoggingService;
	
	@Autowired
	private EmailSmsAuditLogService emailSmsAuditLogService;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		try {
			this.apiLoggingService.beforeRequest(requestWrapper);
			filterChain.doFilter(requestWrapper, responseWrapper);
			emailSmsAuditLogService.manageEmailSmsAuditlogFromResponse(requestWrapper,responseWrapper);
		} catch (Exception e) {
			log.error("Exception:-", e);
		} finally {
			apiLoggingService.afterRequest(requestWrapper, responseWrapper);
			responseWrapper.copyBodyToResponse();
		}
	}
}
