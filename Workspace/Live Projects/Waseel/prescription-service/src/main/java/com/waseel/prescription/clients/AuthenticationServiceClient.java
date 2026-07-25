package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.OneTimeAccessTokenRequest;

@FeignClient(name = "AuthenticationServiceClient", url = "${authenticationServiceClient.url}")
public interface AuthenticationServiceClient {

	@PostMapping("/token/limited")
	public JwtResponse generateAccessTokenForPatientUrl(@RequestBody OneTimeAccessTokenRequest timeAccessTokenRequest,
			@RequestHeader(name = "Authorization") String authorizationHeader);
}
