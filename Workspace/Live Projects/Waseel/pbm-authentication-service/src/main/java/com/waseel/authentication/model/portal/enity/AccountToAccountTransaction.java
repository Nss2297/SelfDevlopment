package com.waseel.authentication.model.portal.enity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`AccountToAccountTransaction`")
public class AccountToAccountTransaction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AccountToAccountTransactionId id;
	private String isBillable;
	private String isEnabled;

	public AccountToAccountTransaction() {
	}

	public AccountToAccountTransaction(AccountToAccountTransactionId id,
			String isBillable, String isEnabled) {
		this.id = id;
		this.isBillable = isBillable;
		this.isEnabled = isEnabled;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "source", column = @Column(name = "`Source`", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "destination", column = @Column(name = "`Destination`", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "transactionId", column = @Column(name = "`TransactionId`", nullable = false, precision = 10, scale = 6)) })
	public AccountToAccountTransactionId getId() {
		return this.id;
	}

	public void setId(AccountToAccountTransactionId id) {
		this.id = id;
	}

	@Column(name = "`IsBillable`", nullable = false, length = 1)
	public String getIsBillable() {
		return this.isBillable;
	}

	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}

	@Column(name = "`IsEnabled`", nullable = false, length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

}
