package com.waseel.policy.persist.hira;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "`AccountToAccountAssociation`", schema = "HIRA")
@Transactional
public class AccountToAccountAssociation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "source", column = @Column(name = "`Source`", nullable = false))
	@AttributeOverride(name = "destination", column = @Column(name = "`Destination`", nullable = false))
	private AccountToAccountAssociationId id;

	public AccountToAccountAssociation() {
	}

	public AccountToAccountAssociation(AccountToAccountAssociationId id, boolean isBillable, boolean isEnabled,
			String code) {
		super();
		this.id = id;
		this.isBillable = isBillable;
		this.isEnabled = isEnabled;
		this.code = code;
	}

	@Column(name = "`IsBillable`", nullable = false, length = 1)
	private boolean isBillable;

	@Column(name = "`IsEnabled`", nullable = false, length = 1)
	private boolean isEnabled;

	@Column(name = "`Code`", nullable = false)
	private String code;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`Source`", referencedColumnName = "`SwitchAccountId`", insertable = false, updatable = false)
	private SwitchAccount switchAccount;

	public boolean getIsBillable() {
		return isBillable;
	}

	public void setIsBillable(boolean isBillable) {
		this.isBillable = isBillable;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public AccountToAccountAssociationId getId() {
		return id;
	}

	public void setId(AccountToAccountAssociationId id) {
		this.id = id;
	}

}
