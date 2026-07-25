package com.waseel.pbmpayerapisservice.clients;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class TawuniyaClientConfigurations {

	@Value("${clients.tawuniya.apiKey}")
	private String tawuniyaApiKey;

	@Value("${clients.tawuniya.senderCode}")
	private String senderCode;

	@Value("${clients.tawuniya.receiverCode}")
	private String receiverCode;

	@Value("${clients.tawuniya.direction}")
	private String direction;

	@Bean
	RequestInterceptor requestInterceptor() {
		return this::applyHeadersForTawuniya;
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	private void applyHeadersForTawuniya(RequestTemplate requestTemplate) {
		try {
			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			if (requestTemplate.feignTarget().type() == TawuniyaClient.class) {
				requestTemplate.header("API-Key", tawuniyaApiKey);
				requestTemplate.header("Sender-Code", senderCode);
				requestTemplate.header("Receiver-Code", receiverCode);
				requestTemplate.header("Time-Stamp", timestamp.toString());
				requestTemplate.header("Transaction-Id", " ");
				requestTemplate.header("Direction", direction);
			}
		} catch (IllegalArgumentException ex) {
			LoggerFactory.getLogger(getClass()).warn("Header value {} was not found.",
					requestTemplate.feignTarget().name(), ex);
		}
	}
}
