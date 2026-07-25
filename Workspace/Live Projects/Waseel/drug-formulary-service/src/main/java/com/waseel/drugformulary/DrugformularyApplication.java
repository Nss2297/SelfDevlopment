package com.waseel.drugformulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableFeignClients
public class DrugformularyApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(DrugformularyApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (DrugformularyApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
