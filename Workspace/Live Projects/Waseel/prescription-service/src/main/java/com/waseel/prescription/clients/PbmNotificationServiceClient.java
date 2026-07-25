package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waseel.prescription.model.notification.EmailNotificationRequestModel;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationRequestModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;

@FeignClient(name = "PbmNotificationServiceClient", url = "${pbmNotificationServiceClient.url}")
public interface PbmNotificationServiceClient {

	@PostMapping("/notifications/sms")
	public ResponseEntity<SmsNotificationResponseModel> sentNotificationToPatient(
			@RequestBody SmsNotificationRequestModel notificationRequestModel);

	@PostMapping("/notifications/email")
	public ResponseEntity<EmailNotificationResponseModel> sendEmailNotification(
			@RequestBody EmailNotificationRequestModel emailNotificationRequestModel);

}
