package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class AccountToAccountAssociationId  implements java.io.Serializable {

	private BigDecimal source;
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
