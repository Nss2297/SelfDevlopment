package com.waseel.pbmnotificationservice.model.sso;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SsoRequestModel implements Serializable {

	private static final long serialVersionUID = 5895451640275578246L;

	private static final String BEARER_TOKEN_TYPE = "Bearer";

	@JsonProperty("access_token")
	private final String accessToken;
	@JsonProperty("refresh_expires_in")
	private final Date refreshExpiresIn;
	@JsonProperty("expires_in")
	private final Date expiresIn;
	@JsonProperty("token_type")
	private final String tokenType;
	@JsonProperty("not-before-policy")
	private final String notBeforePolicy;
	@JsonProperty("scope")
	private final String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public String getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public String getScope() {
		return scope;
	}

	public SsoRequestModel(String accessToken, Date refreshExpiresIn, Date expiresIn, String notBeforePolicy,
			String scope) {
		super();
		this.accessToken = accessToken;
		this.refreshExpiresIn = refreshExpiresIn;
		this.expiresIn = expiresIn;
		this.tokenType = BEARER_TOKEN_TYPE;
		this.notBeforePolicy = notBeforePolicy;
		this.scope = scope;
	}
}
