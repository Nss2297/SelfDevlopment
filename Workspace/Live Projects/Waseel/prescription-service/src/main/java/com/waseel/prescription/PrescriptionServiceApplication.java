package com.waseel.prescription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

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
@OpenAPIDefinition(servers = {
		@Server(url = "${swagger.url}") }, info = @Info(title = "Prescriptions APIs", version = "1.0", description = "Prescriptions APIs documentation"))
@SecurityScheme(name = "waseelOAuth", scheme = "oAuth", type = SecuritySchemeType.OAUTH2, in = SecuritySchemeIn.HEADER, flows = @OAuthFlows(password = @OAuthFlow(authorizationUrl = "${swagger.authorization.url}", tokenUrl = "${swagger.authorization.url}", refreshUrl = "${swagger.refresh.url}")))
@EnableFeignClients
public class PrescriptionServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(PrescriptionServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (PrescriptionServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
