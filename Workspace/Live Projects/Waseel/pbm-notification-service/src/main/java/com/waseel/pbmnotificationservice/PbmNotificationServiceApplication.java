package com.waseel.pbmnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class PbmNotificationServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(PbmNotificationServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (PbmNotificationServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
