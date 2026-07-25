package com.waseel.pbm.fdbvalidationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableRetry
public class FdbValidationServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(FdbValidationServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (FdbValidationServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
