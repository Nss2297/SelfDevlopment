package com.waseel.pbm.authentication.configuration;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.authentication.model.AllowedClientsProperties;
import com.waseel.pbm.authentication.model.PatientUser;
import com.waseel.pbm.authentication.model.User;
import com.waseel.pbm.authentication.service.ApiKeysService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AllowedClientsProperties allowedClients;
	
	@Autowired
	private ApiKeysService apiKeysService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		String tokenOrClientId = getTokenFromRequest(requestWrapper);
		if (tokenOrClientId != null) {
			if (jwtTokenUtil.isJwtToken(tokenOrClientId)) {
				UserDetails user = getUserDetailsFromToken(tokenOrClientId);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				UserDetails user = new org.springframework.security.core.userdetails.User(tokenOrClientId, "", true,
						true, true, true, List.of(new SimpleGrantedAuthority("client")));
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities()));
			}
		}
		chain.doFilter(requestWrapper, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.endsWith("authenticate") || path.endsWith("refresh") || path.endsWith("signup")
				|| path.endsWith("receiveOtp") || path.contains("actuator")
				|| path.endsWith("api-docs") || path.endsWith("csrf");
	}

	private UserDetails getUserDetailsFromToken(String token) {
		try {
			String tokenType = jwtTokenUtil.getTokenType(token);
			Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
			if (tokenType.equals("access_token")) {
				String userId = claims.get("sub", String.class);
				List<LinkedHashMap<String, String>> authorities = claims.get("rol", List.class);
				String accName = claims.get("acc_name", String.class);
				String userFullName = claims.get("user_full_name", String.class);
				String accId = claims.get("acc_id", String.class);
				String accCode = claims.get("acc_code", String.class);
				String accCategory = claims.get("acc_category", String.class);
				String email = claims.get("user_email", String.class);
				return new User(
						userId,
						authorities.parallelStream().map(x -> new SimpleGrantedAuthority(x.get("authority")))
								.collect(Collectors.toList()),
						userFullName, accName,
						accId, accCode, accCategory, email);
			} else if (tokenType.equals("limited_access_token")) {
				String patientId = claims.get("sub", String.class);
				List<LinkedHashMap<String, String>> authorities = claims.get("rol", List.class);
				return new PatientUser(patientId,
						authorities.parallelStream().map(x -> new SimpleGrantedAuthority(x.get("authority")))
								.collect(Collectors.toList()));
			} else if (tokenType.equals("api_key")) {
				String userId = claims.get("sub", String.class);
				String accName = claims.get("acc_name", String.class);
				String accId = claims.get("acc_id", String.class);
				String accCode = claims.get("acc_code", String.class);
				List<GrantedAuthority> authorities = apiKeysService.addAuthoritiesByPayerId(accId);
				return new User(userId, authorities, accName, accId, accCode);
			}
		} catch (IllegalArgumentException e) {
			logger.warn("Unable to get claims from JWT Token, {}", token);
		} catch (ExpiredJwtException e) {
			logger.warn("JWT Token has expired {}", token);
		}
		return null;
	}

	private String getTokenFromRequest(ContentCachingRequestWrapper requestWrapper) throws IOException {
		String authorizationHeader = requestWrapper.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && !authorizationHeader.isBlank()) {
			if (authorizationHeader.startsWith("Basic ")) {
				String[] tokenValues = requestWrapper.getParameterValues("token");
				if (tokenValues != null && tokenValues.length > 0) {
					return tokenValues[0];
				} else {
					return verifyBasicAuth(authorizationHeader.substring(6));
				}
			} else if (authorizationHeader.startsWith("Bearer ")) {
				return authorizationHeader.substring(7);
			}
		}
		logger.warn("Authorization header is null or invalid for request to: {}. Header value: {}",
				requestWrapper.getRequestURL().toString(), authorizationHeader);

		return null;
	}

	private String verifyBasicAuth(String authorizationHeader) {

		String clientIdAndSecret = new String(Base64.getDecoder().decode(authorizationHeader));

		String clientId = clientIdAndSecret.split(":")[0];
		String clientSecret = clientIdAndSecret.split(":")[1];

		return allowedClients.getClients().stream()
				.filter(client -> client.getId().equals(clientId) && client.getSecret().equals(clientSecret))
				.map(client -> client.getId())
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS"));
	}
}
