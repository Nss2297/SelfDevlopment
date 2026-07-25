package com.waseel.pbmnotificationservice.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.waseel.pbmnotificationservice.clients.SsoClient;
import com.waseel.pbmnotificationservice.model.sso.JwtResponse;
import com.waseel.pbmnotificationservice.service.MapperService;

import feign.FeignException;

@Service
public class SsoRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SsoRestHandler.class);

	@Autowired
	private SsoClient ssoClient;

	@Autowired
	private MapperService mapperService;

	public JwtResponse sendMemberDetailsToAuthenticationServiceToGenerateAccessToken(String authorizationHeader,
			 MultiValueMap<String, String> requestBody) {
		try {
			LOGGER.info("Send request to generate access token.");
			return ssoClient.fetchAccessToken(authorizationHeader, requestBody);
		} catch (FeignException e) {
			e.printStackTrace();
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				LOGGER.error(
						"FeignException Has Been Thrown While Reading The Response From SSO service, failed with status [{}]",
						e.status(), e);
				return mapperService.mapJwtResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				LOGGER.error("Not able to call SSO Service, failed with status [{}]", e.status(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception Has Been Thrown While Reading The Response From SSO service  Error: ", e);
		}
		return null;
	}

}
