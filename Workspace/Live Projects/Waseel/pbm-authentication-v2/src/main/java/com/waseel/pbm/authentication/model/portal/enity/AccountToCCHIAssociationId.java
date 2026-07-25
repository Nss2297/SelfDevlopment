package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class AccountToCCHIAssociationId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3848026719734736886L;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "`AccountId`", nullable = false)
	private SwitchAccount accountId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "`CCHIId`", nullable = false)
	private CCHI cchiId;
	
	public AccountToCCHIAssociationId() {
	}

	public AccountToCCHIAssociationId(SwitchAccount accountId, CCHI cchiId) {
		super();
		this.accountId = accountId;
		this.cchiId = cchiId;
	}

	public CCHI getCchiId() {
		return cchiId;
	}

	public void setCchiId(CCHI cchiId) {
		this.cchiId = cchiId;
	}

	public SwitchAccount getAccountId() {
		return accountId;
	}

	public void setAccountId(SwitchAccount accountId) {
		this.accountId = accountId;
	}
}
