package com.waseel.pbm.authentication.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class PbmGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373585756923210548L;

	private String resource;
	private String transactions;

	public PbmGrantedAuthority(String resource, String transactions) {
		super();
		this.resource = resource;
		this.transactions = transactions;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getTransactions() {
		return transactions;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	@Override
	public String getAuthority() {
		if (transactions == null) {
			return this.resource + ";";
		}
		return this.resource + ";" + this.transactions;
	}

}
