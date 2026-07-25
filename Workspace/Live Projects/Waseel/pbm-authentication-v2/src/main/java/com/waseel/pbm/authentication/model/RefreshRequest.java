package com.waseel.pbm.authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshRequest {
	
	@JsonProperty("access_token")
	private final String accessToken;
	@JsonProperty("refresh_token")
	private final String refreshToken;
	
	
	public RefreshRequest(String access_token, String refresh_token) {
		this.accessToken = access_token;
		this.refreshToken = refresh_token;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public String getRefreshToken() {
		return refreshToken;
	}



}
