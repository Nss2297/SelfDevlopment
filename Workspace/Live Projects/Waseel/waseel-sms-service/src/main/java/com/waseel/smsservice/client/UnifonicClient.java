package com.waseel.smsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.waseel.smsservice.model.UnifonicRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "UnifonicClient", url = "${clients.unifonic.url}")
public interface UnifonicClient {

	@PostMapping
	public ResponseEntity<UnifonicResponseModel> sendSmsNotification(@RequestBody UnifonicRequestModel smsRequestModel);
}
