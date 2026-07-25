package com.waseel.pbm.authentication.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.authentication.configuration.JwtTokenUtil;

import io.jsonwebtoken.Claims;

@Component
public class RequestResponseFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		filterChain.doFilter(requestWrapper, responseWrapper);
		try {
			if (request.getMethod().equals("POST") && (response.getStatus() + "").startsWith("2")) {
				String requestBody = (new String(requestWrapper.getContentAsByteArray()));
				String responseBody = (new String(responseWrapper.getContentAsByteArray()));
				if (request.getRequestURI().endsWith("/signup")) {
					log.info("[Audit]: new user is signing up... {}", requestBody);
				} else if (request.getRequestURI().endsWith("/receiveOtp")) {
					Claims jwtClaims = jwtTokenUtil
							.getAllClaimsFromToken(
									getValueFromJsonString(responseBody, Arrays.asList("jwtResponse", "access_token")));
					String providerId = jwtClaims.get("prov_id", String.class);
					String username = jwtClaims.getSubject();
					log.info("[Audit]: new user is signed up successfully for provider [{}], with username {}... {}",
							providerId, username, responseBody);
				}
			}
		} catch (Exception e) {
			log.warn("could not audit request {}, {}", requestWrapper.getRequestURI(),
					requestWrapper.getContentAsByteArray(), e);
		}
		responseWrapper.copyBodyToResponse();
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return !path.endsWith("authenticate") && !path.endsWith("refresh") && !path.endsWith("signup")
				&& !path.endsWith("receiveOtp");
	}

	private String getValueFromJsonString(String jsonString, List<String> fieldKeys) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode json = mapper.readTree(jsonString);
			for (String key : fieldKeys) {
				json = json.get(key);
			}
			return json.asText();
		} catch (JsonProcessingException e) {
			log.warn("exception extracting data from json for audit.", e);
		}
		return null;
	}
}
