package com.waseel.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.elastic.apm.attach.ElasticApmAttacher;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(servers = {
		@Server(url = "https://pqa-portal.waseel.com/api") }, info = @Info(title = "Email APIs", version = "1.0", 
				description = "Email APIs documentation"))
@SecurityScheme(name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT", 
					type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class EmailserviceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(EmailserviceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (EmailserviceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}

}
