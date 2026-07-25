package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.SsoTokenRequest;

@FeignClient(name = "SsoClient", url = "${sso.url}")
public interface SsoClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JwtResponse fetchAccessToken(
			@RequestBody SsoTokenRequest ssoTokenRequest);

}
