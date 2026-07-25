package com.waseel.dssadminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableFeignClients
public class DssadminserviceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(DssadminserviceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (DssadminserviceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}

}
