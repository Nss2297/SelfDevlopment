package com.waseel.pbm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class PbmGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbmGatewayServiceApplication.class, args);
	}

}
