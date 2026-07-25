package com.waseel.smsservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
	private String introspectionUri;
	@Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
	private String clientSecret;

	@Bean
	public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.cors().disable().csrf().disable().authorizeRequests()
				.antMatchers("/actuator/**", "/swagger-ui.html", "/error", "/v2/api-docs", "/swagger-resources/**",
						"/webjars/**")
				.permitAll().anyRequest().hasAuthority("SCOPE_sms-sender").and().exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.oauth2ResourceServer().opaqueToken().introspectionUri(introspectionUri)
				.introspectionClientCredentials(clientId, clientSecret);
		return httpSecurity.build();
	}

}
