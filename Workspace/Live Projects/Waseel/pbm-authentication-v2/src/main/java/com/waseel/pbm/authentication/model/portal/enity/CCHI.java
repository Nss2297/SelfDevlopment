package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "`CCHI`")
public class CCHI {

	@Id
	@Column(name = "`CCHIId`")
	@GeneratedValue(generator = "cchi_seq")
	@SequenceGenerator(name = "cchi_seq", sequenceName = "`CCHI_SEQ`", allocationSize = 1)
	private BigDecimal cchiId;

	@Column(name = "`ProviderId`")
	private BigDecimal providerId;

	@Column(name = "`ExpiryDate`")
	private Date expiryDate;

	public BigDecimal getCchiId() {
		return cchiId;
	}

	public void setCchiId(BigDecimal cchiId) {
		this.cchiId = cchiId;
	}

	public BigDecimal getProviderId() {
		return providerId;
	}

	public void setProviderId(BigDecimal providerId) {
		this.providerId = providerId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
