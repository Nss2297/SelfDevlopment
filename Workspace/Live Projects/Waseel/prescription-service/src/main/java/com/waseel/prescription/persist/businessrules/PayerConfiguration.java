package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PayerConfiguration", schema = "PBM_BUSINESS_RULES")
public class PayerConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PayerId",length = 20)
	private String payerId;

	@Column(name = "PayerName",length = 100)
	private String payerName;

	@Column(name = "IsEnabled",columnDefinition = "CHAR(1) default ('1')")
	private boolean isEnabled = true;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public PayerConfiguration() {
	}

	public PayerConfiguration(String payerId, String payerName, boolean isEnabled) {
		this.payerId = payerId;
		this.payerName = payerName;
		this.isEnabled = isEnabled;
	}
}
