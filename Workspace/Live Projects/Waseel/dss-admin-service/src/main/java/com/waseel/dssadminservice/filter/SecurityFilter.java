package com.waseel.dssadminservice.filter;

import java.io.IOException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Map<String, String> userData = new HashMap<>();
		userData.put("accId", request.getHeader("accId"));
		userData.put("accName", request.getHeader("accName"));
		userData.put("accCode", request.getHeader("accCode"));
		userData.put("username", request.getHeader("username"));
		userData.put("email", request.getHeader("email"));
		userData.put("accCategory", request.getHeader("accCategory"));
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
	public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests().anyRequest().permitAll().and().csrf().disable().build();
	}

}
