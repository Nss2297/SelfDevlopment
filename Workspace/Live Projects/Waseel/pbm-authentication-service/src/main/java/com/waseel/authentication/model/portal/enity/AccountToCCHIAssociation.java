package com.waseel.authentication.model.portal.enity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`AccountToCCHIAssociation`")
public class AccountToCCHIAssociation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3509680548549048640L;
	
	@EmbeddedId
	private AccountToCCHIAssociationId id;
	
	public AccountToCCHIAssociation() {
	}

	public AccountToCCHIAssociation(AccountToCCHIAssociationId id) {
		super();
		this.id = id;
	}

	public AccountToCCHIAssociationId getId() {
		return id;
	}

	public void setId(AccountToCCHIAssociationId id) {
		this.id = id;
	}



}
