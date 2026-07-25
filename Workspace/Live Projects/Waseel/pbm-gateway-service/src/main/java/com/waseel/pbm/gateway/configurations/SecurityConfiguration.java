package com.waseel.pbm.gateway.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.waseel.pbm.gateway.model.RoutesProperties;

@Configuration
public class SecurityConfiguration {

	@Value("${routes.oauthUrl}")
	private String oauthUrl;

	@Autowired
	private RoutesProperties routesProperties;

	@Bean
	public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
		// @formatter:off
		http.httpBasic().disable();
		http.formLogin().disable();
		http.csrf().disable();
		http.cors().configurationSource(corsConfigurationSource());
		http.logout().disable();
		http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
		List<String> paths = routesProperties.getRoutes().stream()
				.filter(route -> route.getPermitPaths() != null && !route.getPermitPaths().isEmpty())
				.map(route -> route.getPermitPaths()).reduce(new ArrayList<String>(), (l1, l2) -> {
					l1.addAll(l2);
					return l1;
				});
		String[] pathsArray = new String[paths.size()];
		http.authorizeExchange().pathMatchers(paths.toArray(pathsArray)).permitAll().anyExchange().authenticated();
		ServerBearerTokenAuthenticationConverter authenticationConverter = new ServerBearerTokenAuthenticationConverter();
		authenticationConverter.setAllowUriQueryParameter(true);
		http.oauth2ResourceServer().bearerTokenConverter(authenticationConverter).opaqueToken()
				.introspectionClientCredentials("GatewayService", "gateway-service-secret")
				.introspectionUri(oauthUrl + "/users/current");
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
