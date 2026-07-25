package com.waseel.waseelurlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class WaseelUrlShortenerApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(WaseelUrlShortenerApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (WaseelUrlShortenerApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}

}
