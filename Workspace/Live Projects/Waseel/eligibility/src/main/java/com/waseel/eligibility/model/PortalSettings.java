package com.waseel.eligibility.model;

import java.io.Serializable;

public class PortalSettings implements Serializable {

	private static final long serialVersionUID = -2237351805864412962L;
	
	private String providerId;
	private String username;
	private String password;
	
	public PortalSettings(String providerId, String username, String password) {
		super();
		this.providerId = providerId;
		this.username = username;
		this.password = password;
	}

	public PortalSettings() {}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
