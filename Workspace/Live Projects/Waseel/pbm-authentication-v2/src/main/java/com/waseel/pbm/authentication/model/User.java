package com.waseel.pbm.authentication.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2811802864775043003L;

	private String userFullName;
	private String accName;
	private String accId;
	private String accCode;
	private String accCategory;
	private Boolean active;
	private String email;



	public User(String username,Collection<? extends GrantedAuthority> authorities,
			String userFullName, String accName, String accId, String accCode, String accCategory, String email) {
		super(username, "", authorities);
		this.userFullName = userFullName;
		this.accName = accName;
		this.accId = accId;
		this.accCode = accCode;
		this.accCategory = accCategory;
		this.email = email;
	}

	public User(String username,Collection<? extends GrantedAuthority> authorities,
		String accName, String accId, String accCode) {
		super(username, "", authorities);
		this.accName = accName;
		this.accId = accId;
		this.accCode = accCode;
	}
	
	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getAccCode() {
		return accCode;
	}

	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	public String getAccCategory() {
		return accCategory;
	}

	public void setAccCategory(String accCategory) {
		this.accCategory = accCategory;
	}

	public Boolean getActive() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
