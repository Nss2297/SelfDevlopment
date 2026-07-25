package com.waseel.pbm.rtsservice.persist.hira;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountToAccountAssociationNonWaseelId implements java.io.Serializable {

	private static final long serialVersionUID = 1376318363020298858L;
	private BigDecimal source;
	private BigDecimal destination;
	private String code;

	public AccountToAccountAssociationNonWaseelId() {
	}

	public AccountToAccountAssociationNonWaseelId(BigDecimal source, BigDecimal destination, String code) {
		this.source = source;
		this.destination = destination;
		this.code = code;
	}

	@Column(name = "Source", nullable = false, precision = 22, scale = 0)
	public BigDecimal getSource() {
		return this.source;
	}

	public void setSource(BigDecimal source) {
		this.source = source;
	}

	@Column(name = "Destination", nullable = false, precision = 22, scale = 0)
	public BigDecimal getDestination() {
		return this.destination;
	}

	public void setDestination(BigDecimal destination) {
		this.destination = destination;
	}

	@Column(name = "Code", nullable = false, length = 30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, destination, source);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountToAccountAssociationNonWaseelId other = (AccountToAccountAssociationNonWaseelId) obj;
		return Objects.equals(code, other.code) && Objects.equals(destination, other.destination)
				&& Objects.equals(source, other.source);
	}

}