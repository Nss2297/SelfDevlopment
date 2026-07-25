package com.waseel.pbmnotificationservice.clients.configuration;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waseel.pbmnotificationservice.clients.TawuniyaClient;

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
	RequestInterceptor requestInterceptorForTawuniya() {
		return this::applyHeadersForTawuniya;
	}

	@Bean
	Logger.Level feignLoggerLevelForTawuniya() {
		return Logger.Level.FULL;
	}

	private void applyHeadersForTawuniya(RequestTemplate requestTemplate) {
		try {
			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			if (requestTemplate.feignTarget().type() == TawuniyaClient.class) {
				requestTemplate.header("Sender-Code", senderCode);
				requestTemplate.header("Receiver-Code", receiverCode);
				requestTemplate.header("Time-Stamp", timestamp.toString());
				requestTemplate.header("Transaction-Id", " ");
				requestTemplate.header("Direction", direction);
				requestTemplate.header("API-Key", tawuniyaApiKey);
			}
		} catch (IllegalArgumentException ex) {
			LoggerFactory.getLogger(getClass()).warn("Header value for {} was not found.",
					requestTemplate.feignTarget().name(), ex);
		}
	}
}
