package com.waseel.pbmnotificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waseel.pbmnotificationservice.clients.configuration.EmailSmsClientConfigurations;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.email.EmailRequestModel;

@FeignClient(name = "EmailServiceClient", url = "${client.EmailServiceClient.url}", 
	configuration = EmailSmsClientConfigurations.class)
public interface EmailServiceClient {

	@PostMapping("/emails")
	public ResponseEntity<EmailNotificationResponseModel> sendEmailNofitication(
			@RequestBody EmailRequestModel emailRequestModel);
}
