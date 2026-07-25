package com.waseel.pbm.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.elastic.apm.attach.ElasticApmAttacher;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@OpenAPIDefinition(servers = {
		@Server(url = "${swagger.url}") }, info = @Info(title = "Authentication APIs", version = "1.0", description = "Authentication APIs and Token Generation APIs documentation"))
@SecurityScheme(name = "waseelOAuth", scheme = "oAuth", type = SecuritySchemeType.OAUTH2, in = SecuritySchemeIn.HEADER, flows = @OAuthFlows(password = @OAuthFlow(authorizationUrl = "${swagger.authorization.url}", tokenUrl = "${swagger.authorization.url}", refreshUrl = "${swagger.refresh.url}")))

public class AuthenticationApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(AuthenticationApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (AuthenticationApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}