package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.AuthenticationServiceClient;
import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.OneTimeAccessTokenRequest;
import com.waseel.prescription.service.mapper.MapperService;

import feign.FeignException;

@Service
public class AuthenticationRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestHandler.class);

	@Autowired
	private AuthenticationServiceClient authenticationServiceClient;

	@Autowired
	private MapperService mapperService;

	public JwtResponse generatePatientAccessToken(String idNumber, OneTimeAccessTokenRequest timeAccessTokenRequest,
			String authorizationHeader) {
		return sendMemberDetailsToAuthenticationServiceToGenerateAccessToken(idNumber, timeAccessTokenRequest,
				authorizationHeader);
	}

	public JwtResponse sendMemberDetailsToAuthenticationServiceToGenerateAccessToken(String idNumber,
			OneTimeAccessTokenRequest timeAccessTokenRequest, String authorizationHeader) {
		try {
			LOGGER.info("Send member IdNumber: {} to Authentication Service", idNumber);
			return authenticationServiceClient.generateAccessTokenForPatientUrl(timeAccessTokenRequest, authorizationHeader);
		} catch (FeignException e) {
			e.printStackTrace();
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				LOGGER.error(
						"FeignException Has Been Thrown While Reading The Response From Authentication service For IdNumber : {}, failed with status [{}]",
						idNumber, e.status(), e);
				return mapperService.mapJwtResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				LOGGER.error("Not able to call Authentication Service for IdNumber : {}, failed with status [{}]",
						idNumber, e.status(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Authentication service For IdNumber :{} Error: {}",
					idNumber, e);
		}
		return null;
	}

}
