package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"PayerConfig\"", schema = "MDSS")

public class PayerConfig implements Serializable {

	private static final long serialVersionUID = -1657053867395947303L;

	@Id
	@Column(name = "`PayerId`", nullable = false, length = 20)
	private String payerId;

	@Column(name = "`PbmPayerType`", length = 20)
	private String pbmPayerType;

	@Column(name = "`isEnabled`")
	private Boolean isEnabled;

	public String getPayerId() {
		return payerId;
	}

	public String getPbmPayerType() {
		return pbmPayerType;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setPbmPayerType(String pbmPayerType) {
		this.pbmPayerType = pbmPayerType;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}