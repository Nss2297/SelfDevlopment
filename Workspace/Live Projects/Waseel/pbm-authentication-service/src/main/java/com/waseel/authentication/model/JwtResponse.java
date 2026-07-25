package com.waseel.authentication.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	
	private static final String BEARER_TOKEN_TYPE = "Bearer";
	
	@JsonProperty("access_token")
	private final String accessToken;
	@JsonProperty("refresh_token")
	private final String refreshToken;
	@JsonProperty("token_type")
	private final String tokenType;
	@JsonProperty("expires_in")
	private final Date expiresIn;

	public JwtResponse(String access_token, String refresh_token, Date expires_in) {
		this.accessToken = access_token;
		this.refreshToken = refresh_token;
		this.expiresIn = expires_in;
		this.tokenType = BEARER_TOKEN_TYPE;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}	
	
}
