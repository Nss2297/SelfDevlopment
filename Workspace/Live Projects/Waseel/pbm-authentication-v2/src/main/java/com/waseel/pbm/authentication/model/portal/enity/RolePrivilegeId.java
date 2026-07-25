package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * RolePrivilegeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class RolePrivilegeId implements java.io.Serializable {

	// Fields

	private BigDecimal source;
	private BigDecimal destination;
	private Double transactionId;
	private Role role;

	// Constructors

	/** default constructor */
	public RolePrivilegeId() {
	}

	/** full constructor */
	public RolePrivilegeId(BigDecimal source, BigDecimal destination,
			Double transactionId, Role role) {
		this.source = source;
		this.destination = destination;
		this.transactionId = transactionId;
		this.role = role;
	}

	// Property accessors

	@Column(name = "`Source`", nullable = false, precision = 22, scale = 0)
	public BigDecimal getSource() {
		return this.source;
	}

	public void setSource(BigDecimal source) {
		this.source = source;
	}

	@Column(name = "`Destination`", nullable = false, precision = 22, scale = 0)
	public BigDecimal getDestination() {
		return this.destination;
	}

	public void setDestination(BigDecimal destination) {
		this.destination = destination;
	}

	@Column(name = "`TransactionId`", nullable = false, precision = 10, scale = 6)
	public Double getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Double transactionId) {
		this.transactionId = transactionId;
	}

	@ManyToOne
	@JoinColumn(name = "`RoleId`")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
