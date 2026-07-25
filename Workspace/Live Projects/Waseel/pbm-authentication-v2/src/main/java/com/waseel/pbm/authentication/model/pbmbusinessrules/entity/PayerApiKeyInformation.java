package com.waseel.pbm.authentication.model.pbmbusinessrules.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYER_API_KEY_INFORMATION", schema = "PBM_BUSINESS_RULES")
public class PayerApiKeyInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYER_API_KEY_INFORMATION_ID", unique = true)
	private Long payerApiKeyInformationId;

	@Column(name = "PAYER_ID",nullable = false, precision = 10, scale = 2)
	private String payerId;

	@Column(name = "PAYER_NAME",nullable = false, length = 100)
	private String payerName;

	@Column(name = "STANDARD_TRANSACTION_ID",nullable = false,precision = 16, scale = 6)
	private BigDecimal standardTransactionId;

	@Column(name = "STANDARD_TRANSACTION_NAME",nullable = false,length = 80)
	private String standardTransactionName;

	@Column(name = "API_KEY",length = 3000)
	private String apiKey;

	public Long getPayerApiKeyInformationId() {
		return payerApiKeyInformationId;
	}

	public void setPayerApiKeyInformationId(Long payerApiKeyInformationId) {
		this.payerApiKeyInformationId = payerApiKeyInformationId;
	}

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

	public BigDecimal getStandardTransactionId() {
		return standardTransactionId;
	}

	public void setStandardTransactionId(BigDecimal standardTransactionId) {
		this.standardTransactionId = standardTransactionId;
	}

	public String getStandardTransactionName() {
		return standardTransactionName;
	}

	public void setStandardTransactionName(String standardTransactionName) {
		this.standardTransactionName = standardTransactionName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
