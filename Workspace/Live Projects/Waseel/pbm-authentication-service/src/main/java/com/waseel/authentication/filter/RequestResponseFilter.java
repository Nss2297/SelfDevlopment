package com.waseel.authentication.filter;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.audit.model.AuditLog;
import com.waseel.audit.model.LoginAuditLog;
import com.waseel.authentication.configuration.JwtTokenUtil;

import io.jsonwebtoken.Claims;

@Component
public class RequestResponseFilter extends OncePerRequestFilter {

	
	@Autowired
	private ObjectMapper objectMapper;
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//
//	@Autowired
//	private Queue auditTrailQueue;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		Principal principal = request.getUserPrincipal();
		
		String url = requestWrapper.getRequestURI().toString();
		filterChain.doFilter(requestWrapper, responseWrapper);

//		String responseBody = IOUtils.toString(responseWrapper.getContentInputStream());
//		JsonNode responseJson = objectMapper.readTree(responseBody);
//
//		String requestBody = IOUtils.toString(requestWrapper.getContentAsByteArray());
//		JsonNode requestJson;
//		if (requestBody.contains("{")) {
//			requestJson = objectMapper.readTree(requestWrapper.getContentAsByteArray());
//		} else {
//			String[] keysValues = requestBody.split("&");
//			if(keysValues.length >= 3) {
//				requestJson = objectMapper.readTree("{\"username\":\"" + keysValues[1].split("=")[1] + "\",\"password\":\""
//						+ keysValues[2].split("=")[1] + "\"}");
//			} else {
//				requestJson = objectMapper.readTree("{\"username\":\"" + keysValues[0].split("=")[1] + "\",\"password\":\""
//						+ keysValues[1].split("=")[1] + "\"}");
//			}
//		}

//		if (response.getStatus() >= 200 && response.getStatus() < 300) {
//
//			AuditLog newAuditLog = getAuditLog(responseJson, requestJson, principal, url);
//			if (newAuditLog != null) {
//				try {
//					amqpTemplate.convertAndSend(auditTrailQueue.getName(), newAuditLog);
//				} catch (AmqpConnectException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		}
		responseWrapper.copyBodyToResponse();
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return !path.endsWith("PBMAuthenticate") && !path.endsWith("refresh");
	}

	private AuditLog getAuditLog(JsonNode responseJson, JsonNode requestJson, Principal principal, String url) {
		LoginAuditLog auditLog = null;
		if (url.endsWith("PBMAuthenticate") || url.endsWith("refresh")) {
			auditLog = new LoginAuditLog();
			auditLog.setUserId(
					url.endsWith("refresh") ? getUserIdFromJWT(responseJson) : requestJson.get("username").asText());
			auditLog.setEventDescription(
					"User Has " + (url.endsWith("PBMAuthenticate") ? "Loged In." : "Refreshed The Token"));
			auditLog.setEventPath(url);
			auditLog.setEventTimeStamp(new Timestamp(System.currentTimeMillis()));
			auditLog.setProviderId(getProviderIdFromJWT(responseJson));
			auditLog.setObjectId("");
		}
		return auditLog;
	}

	private String getUserIdFromJWT(JsonNode responseJson) {
		Claims claims = jwtTokenUtil.getAllClaimsFromToken(responseJson.get("access_token").asText());
		String userId = claims.get("src", String.class);

		return userId;
	}

	private String getProviderIdFromJWT(JsonNode responseJson) {
		Claims claims = jwtTokenUtil.getAllClaimsFromToken(responseJson.get("access_token").asText());
		String providerId = claims.get("prov_id", String.class);

		return providerId;
	}

}
