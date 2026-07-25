package com.waseel.pbm.dssservice.aspects;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbm.dssservice.service.managementservice.RestApiLoggingService;
import com.waseel.pbm.dssservice.service.managementservice.TransactionLogService;

@Component
public class ContentCachingFilter extends OncePerRequestFilter {

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    private RestApiLoggingService restApiLoggingService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            restApiLoggingService.beforeRequest(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
            /* Used to hide transactionLog Id from response and also for update
             * transactionLog table and monoDb collection*/
            transactionLogService.manageTransactionLogIdFromResponse(request, response,
                    responseWrapper, requestWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            restApiLoggingService.afterRequest(requestWrapper, responseWrapper);
        }
    }
}
