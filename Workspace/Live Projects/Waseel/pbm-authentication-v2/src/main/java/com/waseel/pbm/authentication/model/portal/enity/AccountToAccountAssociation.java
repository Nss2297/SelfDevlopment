package com.waseel.pbm.authentication.model.portal.enity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "`AccountToAccountAssociation`")
@Transactional
public class AccountToAccountAssociation {

	private AccountToAccountAssociationId id;
	
	private String isBillable;
	
	private String isEnabled;
	
	private String code;
	
	@Column(name = "`IsBillable`", nullable = false, length = 1)
	public String getIsBillable() {
		return isBillable;
	}
	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}
	
	@Column(name = "`IsEnabled`", nullable = false, length = 1)
	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Column(name = "`Code`", nullable = false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@EmbeddedId
	@AttributeOverrides({
	@AttributeOverride(name = "source", column = @Column(name = "`Source`", nullable = false)),
	@AttributeOverride(name = "destination", column = @Column(name = "`Destination`", nullable = false)) })
	public AccountToAccountAssociationId getId() {
		return id;
	}
	public void setId(AccountToAccountAssociationId id) {
		this.id = id;
	}
	
}
