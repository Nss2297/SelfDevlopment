package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountToAccountTransactionId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal source;
	private BigDecimal destination;
	private Double transactionId;

	public AccountToAccountTransactionId() {
	}

	public AccountToAccountTransactionId(BigDecimal source,
			BigDecimal destination, Double transactionId) {
		this.source = source;
		this.destination = destination;
		this.transactionId = transactionId;
	}

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
}