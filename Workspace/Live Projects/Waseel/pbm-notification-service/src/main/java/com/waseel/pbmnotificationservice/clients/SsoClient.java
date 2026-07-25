package com.waseel.pbmnotificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.waseel.pbmnotificationservice.model.sso.JwtResponse;

import feign.Headers;

@FeignClient(name = "SsoClient", url = "${sso.url}")
public interface SsoClient {

	@PostMapping
	@Headers(value = "Content-Type:application/x-www-form-urlencoded")
	public JwtResponse fetchAccessToken(
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
			 @RequestBody MultiValueMap<String, String> requestBody);
	
}
