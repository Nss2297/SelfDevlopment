package com.waseel.prescription.filter;

import com.waseel.prescription.service.management.RestApiLoggingService;
import com.waseel.prescription.service.management.TransactionLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

@Component
@Order(2)
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
            Timestamp sendingTime = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
            if (!request.getRequestURI().contains("actuator") && !request.getRequestURI().contains("v3")) {
                this.apiLoggingService.beforeRequest(requestWrapper);
            }
            filterChain.doFilter(requestWrapper, responseWrapper);
            transactionLogService.manageTransactionLogFromResponse(request, responseWrapper, requestWrapper,
                    sendingTime);
        } catch (Exception e) {
            log.error("Exception:-", e);
        } finally {
            if (!request.getRequestURI().contains("actuator") && !request.getRequestURI().contains("v3")) {
                apiLoggingService.afterRequest(requestWrapper, responseWrapper);
            }
            responseWrapper.copyBodyToResponse();
        }
    }
}
