package com.waseel.pbmschedulerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableScheduling
@EnableRetry
@EnableFeignClients
public class PbmSchedulerServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(PbmSchedulerServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (PbmSchedulerServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
