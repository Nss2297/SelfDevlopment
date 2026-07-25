package com.waseel.authentication.model.portal.enity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolePrivilegeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class RolePrivilegeId implements java.io.Serializable {

	// Fields

	private BigDecimal source;
	private BigDecimal destination;
	private Double transactionId;
	private BigDecimal roleId;

	// Constructors

	/** default constructor */
	public RolePrivilegeId() {
	}

	/** full constructor */
	public RolePrivilegeId(BigDecimal source, BigDecimal destination,
			Double transactionId, BigDecimal roleId) {
		this.source = source;
		this.destination = destination;
		this.transactionId = transactionId;
		this.roleId = roleId;
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

	@Column(name = "`RoleId`", nullable = false, precision = 22, scale = 0)
	public BigDecimal getRoleId() {
		return this.roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolePrivilegeId))
			return false;
		RolePrivilegeId castOther = (RolePrivilegeId) other;

		return ((this.getSource() == castOther.getSource()) || (this
				.getSource() != null && castOther.getSource() != null && this
				.getSource().equals(castOther.getSource())))
				&& ((this.getDestination() == castOther.getDestination()) || (this
						.getDestination() != null
						&& castOther.getDestination() != null && this
						.getDestination().equals(castOther.getDestination())))
				&& ((this.getTransactionId() == castOther.getTransactionId()) || (this
						.getTransactionId() != null
						&& castOther.getTransactionId() != null && this
						.getTransactionId()
						.equals(castOther.getTransactionId())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this
						.getRoleId() != null && castOther.getRoleId() != null && this
						.getRoleId().equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSource() == null ? 0 : this.getSource().hashCode());
		result = 37
				* result
				+ (getDestination() == null ? 0 : this.getDestination()
						.hashCode());
		result = 37
				* result
				+ (getTransactionId() == null ? 0 : this.getTransactionId()
						.hashCode());
		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}
