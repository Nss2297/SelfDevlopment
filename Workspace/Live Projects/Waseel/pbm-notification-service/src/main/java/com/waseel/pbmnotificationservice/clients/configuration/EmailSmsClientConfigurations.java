package com.waseel.pbmnotificationservice.clients.configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.waseel.pbmnotificationservice.clients.EmailServiceClient;
import com.waseel.pbmnotificationservice.clients.WaseelSmsServiceClient;
import com.waseel.pbmnotificationservice.model.enums.RequestHeaderDetails;
import com.waseel.pbmnotificationservice.model.sso.JwtResponse;
import com.waseel.pbmnotificationservice.service.clienthandler.SsoRestHandler;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class EmailSmsClientConfigurations {

	@Autowired
	private SsoRestHandler ssoRestHandler;

	@Value("${sso.auth.username}")
	private String ssoUsername;

	@Value("${sso.auth.password}")
	private String ssoPassword;

	@Value("${sso.body.key}")
	private String ssoRequestBodyKey;

	@Value("${sso.body.value}")
	private String ssoRequestBodyValue;

	@Bean
	RequestInterceptor requestInterceptorForSMSAndEmail() {
		return this::applyAuthorizationForEmailAndSMS;
	}

	@Bean
	Logger.Level feignLoggerLevelForSMSAndEmail() {
		return Logger.Level.FULL;
	}

	private void applyAuthorizationForEmailAndSMS(RequestTemplate requestTemplate) {
		try {
			if (requestTemplate.feignTarget().type() == EmailServiceClient.class
					|| requestTemplate.feignTarget().type() == WaseelSmsServiceClient.class) {
				requestTemplate.header(HttpHeaders.AUTHORIZATION, generateAuthorizationToken());
			}
		} catch (IllegalArgumentException ex) {
			LoggerFactory.getLogger(getClass()).warn("Header value for {} was not found.",
					requestTemplate.feignTarget().name(), ex);
		}
	}

	private String generateAuthorizationToken() {
		JwtResponse jwtResponse = ssoRestHandler.sendMemberDetailsToAuthenticationServiceToGenerateAccessToken(
				generateBasicAuthorization(), generateRequestBody());
		String strBearer = RequestHeaderDetails.AUTHORIZATION_BEARER.value();
		if (null != jwtResponse && StringUtils.isNotBlank(jwtResponse.getAccessToken())) {
			return strBearer + jwtResponse.getAccessToken();
		}
		return strBearer + "";
	}

	private String generateBasicAuthorization() {
		String credentials = ssoUsername + ":" + ssoPassword;
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes(StandardCharsets.US_ASCII));
		return RequestHeaderDetails.AUTHORIZATION_BASIC.value() + new String(encodedCredentials);
	}

	private MultiValueMap<String, String> generateRequestBody() {
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		List<String> keys = new ArrayList<>();
		keys.add(ssoRequestBodyValue);
		requestBody.put(ssoRequestBodyKey, keys);
		return requestBody;
	}
}
