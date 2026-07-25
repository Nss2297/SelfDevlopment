package com.waseel.authentication.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.waseel.authentication.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String ip = request.getRemoteAddr();
		String userId = null;
		String providerName = null;
		String fullanme = null;
		String providerId = null;
		String cchiId = null;
		String providerCode = null;
		Claims claims = null;
		List<LinkedHashMap<String, String>> authorities = null;
		Map<String, String> payers = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
				userId = claims.get("sub", String.class);
				authorities = claims.get("rol", List.class);
				payers = claims.get("payers", HashMap.class);
				providerName = claims.get("prov", String.class);
				fullanme = claims.get("full_name", String.class);
				providerId = claims.get("prov_id", String.class);
				cchiId = claims.get("cchi_id", String.class);
				providerCode = claims.get("prov_code", String.class);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String on path: " + request.getRequestURL().toString());
			logger.warn("Token was: " + requestTokenHeader);
		}
		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = new User(
					userId, providerName, fullanme, authorities.parallelStream()
							.map(x -> new SimpleGrantedAuthority(x.get("authority"))).collect(Collectors.toList()),
					payers, providerId, cchiId, providerCode);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.endsWith("PBMAuthenticate") || path.endsWith("refresh") || path.contains("actuator")
				|| path.contains("swagger") || path.endsWith("api-docs") || path.endsWith("csrf");
	}
}
