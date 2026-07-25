package com.waseel.eligibility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication()
public class EligibilityServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(EligibilityServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (EligibilityServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
