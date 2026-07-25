package com.waseel.authentication.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2811802864775043003L;
	
	private String providerName;
	private String fullName;
	private String providerId;
	private String cchiId;
	private String providerCode;
	private Map<String, String> payers;

	public User(String userId, String providerName, String fullname, Collection<? extends GrantedAuthority> authorities, Map<String, String> payers, String providerId, String cchiId, String providerCode) {
		super(userId, "", authorities);
		this.setProviderName(providerName);
		this.setFullName(fullname);
		this.setPayers(payers);
		this.setProviderId(providerId);
		this.setCchiId(cchiId);
		this.setProviderCode(providerCode);
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Map<String, String> getPayers() {
		return payers;
	}

	public void setPayers(Map<String, String> payers) {
		this.payers = payers;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getCchiId() {
		return cchiId;
	}

	public void setCchiId(String cchiId) {
		this.cchiId = cchiId;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
}
