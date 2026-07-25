package com.waseel.pbm.authentication.model.portal.enity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

/**
 * SwitchUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "`SwitchUser`")
@Transactional
public class SwitchUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String switchUserId;
	private Role role;
	private String firstName;
	private String lastName;
	private Timestamp lastModifiedDate;
	private String password;
	private String email;
	private String isEnabled;
	private String isBillable;
	private String isDeleted;
	private String lastSeenFrom;

	public SwitchUser() {
	}

	public SwitchUser(String switchUserId, Role role,
			Timestamp lastModifiedDate, String password, String isEnabled,
			String isBillable) {
		this.switchUserId = switchUserId;
		this.role = role;
		this.lastModifiedDate = lastModifiedDate;
		this.password = password;
		this.isEnabled = isEnabled;
		this.isBillable = isBillable;
	}

	public SwitchUser(String switchUserId, Role role, String firstName,
			String lastName, Timestamp lastModifiedDate, String password,
			String email, String isEnabled, String isBillable,
			String lastSeenFrom) {
		this.switchUserId = switchUserId;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastModifiedDate = lastModifiedDate;
		this.password = password;
		this.email = email;
		this.isEnabled = isEnabled;
		this.isBillable = isBillable;
		this.lastSeenFrom = lastSeenFrom;
	}

	@Id
	@Column(name = "`SwitchUserId`", unique = true, nullable = false, length = 30)
	public String getSwitchUserId() {
		return this.switchUserId;
	}

	public void setSwitchUserId(String switchUserId) {
		this.switchUserId = switchUserId;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL )
	@JoinColumn(name = "`RoleId`", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "`FirstName`", length = 30)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "`LastName`", length = 30)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "`LastModifiedDate`", length = 7)
	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Column(name = "`Password`", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "`Email`", length = 256)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "`IsEnabled`", nullable = false, length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "`IsBillable`", nullable = false, length = 1)
	public String getIsBillable() {
		return this.isBillable;
	}

	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}

	@Column(name = "`LastSeenFrom`", length = 100)
	public String getLastSeenFrom() {
		return this.lastSeenFrom;
	}

	public void setLastSeenFrom(String lastSeenFrom) {
		this.lastSeenFrom = lastSeenFrom;
	}

	@Column(name = "`IsDeleted`", nullable = false, length = 1)
	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
