package com.waseel.pbmnotificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.waseel.pbmnotificationservice.clients.configuration.EmailSmsClientConfigurations;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicRequestModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "WaseelSmsServiceClient", url = "${waseelSmsService.url}",
	configuration = EmailSmsClientConfigurations.class)
public interface WaseelSmsServiceClient {

	@PostMapping("/sms/send")
	public ResponseEntity<UnifonicReponseModel> sendMemberDetailsToUnifonic(
			@RequestBody UnifonicRequestModel unifoniRequestModel);
}
