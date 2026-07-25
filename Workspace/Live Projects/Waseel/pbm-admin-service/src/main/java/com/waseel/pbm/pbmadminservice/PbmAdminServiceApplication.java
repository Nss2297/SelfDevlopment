package com.waseel.pbm.pbmadminservice;

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
		@Server(url = "https://qa-pbm-admin.waseel.com/api") }, info = @Info(title = "PBM Admin APIs", version = "1.0", description = "PBM Admin APIs documentation"))
@SecurityScheme(name = "waseelOAuth", scheme = "oAuth", type = SecuritySchemeType.OAUTH2, in = SecuritySchemeIn.HEADER, flows = @OAuthFlows(password = @OAuthFlow(authorizationUrl = "https://qa-pbm-admin.waseel.com/api/oauth/signIn", tokenUrl = "https://qa-pbm-admin.waseel.com/api/oauth/signIn", refreshUrl = "https://qa-pbm-admin.waseel.com/api/oauth/refresh")))
@EnableFeignClients
public class PbmAdminServiceApplication {

	public static void main(String[] args) {
		String[] profiles = SpringApplication.run(PbmAdminServiceApplication.class, args).getEnvironment()
				.getActiveProfiles();
		for (int i = 0; i < profiles.length; i++) {
			String filename = "elasticapm-" + profiles[i] + ".properties";
			if (PbmAdminServiceApplication.class.getClassLoader().getResource(filename) == null)
				continue;
			ElasticApmAttacher.attach(filename);
		}
	}
}
