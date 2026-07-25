package com.waseel.pbm.dssservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class DSSServiceApplication {

    public static void main(String[] args) {
        String[] profiles = SpringApplication.run(DSSServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (DSSServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
    }
}
