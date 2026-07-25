package com.waseel.prescription.persist.hira;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountToAccountAssociationId  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "Source", nullable = false)
	private BigDecimal source;

	@Column(name = "Destination", nullable = false)
	private BigDecimal destination;
	
	public AccountToAccountAssociationId() {
	}
	
	public AccountToAccountAssociationId(BigDecimal source, BigDecimal destination) {
		this.source = source;
		this.destination = destination;
	}

	public BigDecimal getSource() {
		return source;
	}
	public void setSource(BigDecimal source) {
		this.source = source;
	}
	public BigDecimal getDestination() {
		return destination;
	}
	public void setDestination(BigDecimal destination) {
		this.destination = destination;
	}
}
