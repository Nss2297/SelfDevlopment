package com.waseel.prescription.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component

@Order(1)

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Map<String, String> userData = new HashMap<>();
		userData.put("accId", request.getHeader("accId"));
		userData.put("accName", decodeValue(request.getHeader("accName")));
		userData.put("accCode", request.getHeader("accCode"));
		userData.put("username", request.getHeader("username"));
		userData.put("email", request.getHeader("email"));
		userData.put("patientId", request.getHeader("patientId"));
		userData.put("authority", request.getHeader("authority"));
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (request.getHeader("authorities") != null) {
			authorities = Arrays.asList(request.getHeader("authorities").split(",")).stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userData, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(user);
		filterChain.doFilter(request, response);
	}

	@Bean
	DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests().anyRequest().permitAll().and().csrf().disable().build();
	}

	private String decodeValue(String value) {
		try {
			return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			return value;
		}
	}
}
