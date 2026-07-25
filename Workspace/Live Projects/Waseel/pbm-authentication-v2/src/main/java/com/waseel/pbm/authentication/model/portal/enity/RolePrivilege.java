package com.waseel.pbm.authentication.model.portal.enity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

/**
 * RolePrivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "`RolePrivilege`")
@Transactional
public class RolePrivilege implements java.io.Serializable {

	// Fields

	private RolePrivilegeId id;
	//private Role role;

	// Constructors

	/** default constructor */
	public RolePrivilege() {
	}

	/** full constructor */
	public RolePrivilege(RolePrivilegeId id/*, Role role*/) {
		this.id = id;
		//this.role = role;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "source", column = @Column(name = "`Source`", nullable = false, precision = 22, scale = 0)),
		@AttributeOverride(name = "destination", column = @Column(name = "`Destination`", nullable = false, precision = 22, scale = 0)),
		@AttributeOverride(name = "transactionId", column = @Column(name = "`TransactionId`", nullable = false, precision = 10, scale = 6)),
		@AttributeOverride(name = "roleId", column = @Column(name = "`RoleId`", nullable = false, precision = 22, scale = 0)) })
	public RolePrivilegeId getId() {
		return this.id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "RoleId", nullable = false, insertable = false, updatable = false)
//	public Role getRole() {
//		return this.role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}

}
