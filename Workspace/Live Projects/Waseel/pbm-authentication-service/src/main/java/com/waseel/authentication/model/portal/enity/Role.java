package com.waseel.authentication.model.portal.enity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "`Role`")
@Transactional
public class Role implements java.io.Serializable {

	// Fields

	private BigDecimal roleId;
	private String description;
	//private Set<SwitchUser> switchUsers = new HashSet<SwitchUser>(0);
	private Set<RolePrivilege> rolePrivileges = new HashSet<RolePrivilege>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(BigDecimal roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public Role(BigDecimal roleId, String description,
//			Set<SwitchUser> switchUsers, 
			Set<RolePrivilege> rolePrivileges) {
		this.roleId = roleId;
		this.description = description;
		//this.switchUsers = switchUsers;
		this.rolePrivileges = rolePrivileges;
	}

	// Property accessors
	@Id
	@Column(name = "`RoleId`", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRoleId() {
		return this.roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	@Column(name = "`Description`", length = 75)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
//	public Set<SwitchUser> getSwitchUsers() {
//		return this.switchUsers;
//	}
//
//	public void setSwitchUsers(Set<SwitchUser> switchUsers) {
//		this.switchUsers = switchUsers;
//	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="`RoleId`")
	public Set<RolePrivilege> getRolePrivileges() {
		return this.rolePrivileges;
	}

	public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

}
