package com.waseel.pbm.authentication.configuration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.waseel.pbm.authentication.model.OneTimeAccessTokenRequest;
import com.waseel.pbm.authentication.model.PbmGrantedAuthority;
import com.waseel.pbm.authentication.model.portal.enity.SwitchAccount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;

	// TODO: make JWT_TOKEN_VALIDITY & JWT_REFRESH_TOKEN_VALIDITY configurable
	public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60 * 1000L;
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 2 * 60 * 60 * 1000L;
	public static final long JWT_SHORT_TOKEN_VALIDITY = 12000;

	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getTokenType(String token) {
		return Jwts.parser().setSigningKey(secret).parse(token).getHeader().get("type").toString();
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public Header<?> getAllHeadersFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parse(token).getHeader();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateLimitedAccessToken(OneTimeAccessTokenRequest request, String clientId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", request.getPatientId());
		List<SimpleGrantedAuthority> roles = request.getObjectIds().stream()
				.map(objectId -> new SimpleGrantedAuthority(clientId + "|" + objectId)).collect(Collectors.toList());
		claims.put("rol", roles);
		return Jwts.builder()
				.setClaims(claims)
				.setHeaderParam("type", "limited_access_token")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + request.getDuration().longValue()))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String generateShortToken(String providerId, Map<BigDecimal, String> payers) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("src", providerId);
		claims.put("sub", providerId);
		claims.put("payers", payers);
		claims.put("prov", providerId);
		claims.put("full_name", providerId);
		claims.put("prov_id", providerId);
		claims.put("cchi_id", providerId);
		claims.put("username", providerId);
		List<SimpleGrantedAuthority> rols = new ArrayList<>();
		rols.add(new SimpleGrantedAuthority(providerId + "|3.0|101"));
		payers.forEach((key, value) -> rols.add(new SimpleGrantedAuthority(providerId + "|3.0|" + key.toString())));
		claims.put("rol", rols);
		return doGenerateToken(claims);
	}

	// generate token for user
	public String generateToken(String userName, Map<String, Object> claims) {
		return doGenerateToken(claims, userName);
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setHeaderParam("type", "access_token").setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String generateRefreshToken(String token) {
		return Jwts.builder().setHeaderParam("type", "refresh_token").setSubject(token)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private String doGenerateToken(Map<String, Object> claims) {
		return Jwts.builder().setHeaderParam("type", "access_token").setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_SHORT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Boolean validateRefreshToken(String refreshToken) {
		return !isTokenExpired(refreshToken);
	}

	public Boolean isJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parse(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String generateApiKey(SwitchAccount account) {
		Map<String, Object> claims = generateJwtClaimsForPayer(account.getName(), account.getCode(),
				account.getSwitchAccountId().toString());
		return Jwts.builder()
				.setHeaderParam("type", "api_key")
				.setClaims(claims)
				.setSubject(account.getName())
				.setIssuedAt(new Date())
				.setExpiration(null)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Map<String, Object> generateJwtClaimsForPayer(String accountName, String accountCode, String accountId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("acc_name", accountName);
		claims.put("acc_code", accountCode);
		claims.put("acc_id", accountId);
		return claims;
	}
}
