package com.waseel.smsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.smsservice.model.sms.SmsResponseModel;

@FeignClient(name = "SmsClient", url = "${clients.waseel-sms.url}")
public interface SmsClient {
	@PostMapping
	public SmsResponseModel sendSmsNotification(@RequestParam(name = "key") String key,
			@RequestParam(name = "number") String number, @RequestParam(name = "message") String message,
			@RequestParam(name = "type") String type);
}
