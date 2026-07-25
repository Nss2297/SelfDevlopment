package com.waseel.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
@EnableScheduling
public class AuthenticationApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(AuthenticationApplication.class, args).getEnvironment().getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (AuthenticationApplication.class.getClassLoader().getResource(filename) == null)
				continue;
		//	ElasticApmAttacher.attach(filename);
		}
	}
}