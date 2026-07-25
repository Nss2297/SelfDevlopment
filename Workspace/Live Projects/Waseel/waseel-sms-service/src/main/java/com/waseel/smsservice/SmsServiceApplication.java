package com.waseel.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableFeignClients

public class SmsServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(SmsServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (SmsServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}

}
