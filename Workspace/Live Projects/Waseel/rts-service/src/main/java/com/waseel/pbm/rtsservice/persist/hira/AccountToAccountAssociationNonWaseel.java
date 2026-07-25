package com.waseel.pbm.rtsservice.persist.hira;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "AccountToAccountAssociationNonWaseel", schema = "HIRA")
@NamedQueries(@NamedQuery(name = "AccountToAccountAssociationNonWaseel.findPayersAssociated", query = "select model from AccountToAccountAssociationNonWaseel model where model.id.source=:accountId and model.isEnabled=1 order by model.id.destination asc"))
public class AccountToAccountAssociationNonWaseel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AccountToAccountAssociationNonWaseelId id;
	private String isBillable;
	private String isEnabled;
	private String name;

	public AccountToAccountAssociationNonWaseel() {
	}

	public AccountToAccountAssociationNonWaseel(AccountToAccountAssociationNonWaseelId id, String code,
			String isBillable, String isEnabled) {
		this.id = id;
		this.isBillable = isBillable;
		this.isEnabled = isEnabled;
	}

	public AccountToAccountAssociationNonWaseel(AccountToAccountAssociationNonWaseelId id, String code,
			String isBillable, String isEnabled, String name) {
		this.id = id;
		this.isBillable = isBillable;
		this.isEnabled = isEnabled;
		this.name = name;
	}

	@EmbeddedId

	@AttributeOverrides({

			@AttributeOverride(name = "source", column = @Column(name = "Source", nullable = false, precision = 22, scale = 0)),

			@AttributeOverride(name = "destination", column = @Column(name = "Destination", nullable = false, precision = 22, scale = 0)),

			@AttributeOverride(name = "code", column = @Column(name = "Code", nullable = false, precision = 22, scale = 0)) })
	public AccountToAccountAssociationNonWaseelId getId() {
		return this.id;
	}

	public void setId(AccountToAccountAssociationNonWaseelId id) {
		this.id = id;
	}

	@Column(name = "IsBillable", nullable = false, length = 1)
	public String getIsBillable() {
		return this.isBillable;
	}

	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}

	@Column(name = "IsEnabled", nullable = false, length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "NAME", nullable = false, precision = 100, scale = 0)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}