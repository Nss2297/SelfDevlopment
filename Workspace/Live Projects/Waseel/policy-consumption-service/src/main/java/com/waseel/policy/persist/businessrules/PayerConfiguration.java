package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "PayerConfiguration" database table.
 * 
 */
@Entity
@Table(name="\"PayerConfiguration\"")
@NamedQuery(name="PayerConfiguration.findAll", query="SELECT p FROM PayerConfiguration p")
public class PayerConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"PayerId\"")
	private String payerId;

	@Column(name="\"IsEnabled\"")
	private String isEnabled;

	@Column(name="\"PayerName\"")
	private String payerName;

	public PayerConfiguration() {
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getPayerName() {
		return this.payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

}