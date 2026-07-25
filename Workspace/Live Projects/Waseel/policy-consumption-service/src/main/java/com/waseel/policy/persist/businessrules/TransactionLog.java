package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TRANSACTION_LOG database table.
 * 
 */
@Entity
@Table(name = "TRANSACTION_LOG")
public class TransactionLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACTION_LOG_ID")
	private long transactionLogId;

	@Column(name = "HTTP_STATUS")
	private String httpStatus;

	@Column(name = "HTTP_STATUS_DESCRIPTION")
	private String httpStatusDescription;

	@Column(name = "PAYER_ID")
	private String payerId;

	@Column(name = "PROVIDER_ID")
	private String providerId;

	@Column(name = "RECEIVING_REQUEST_DATE_TIME")
	private Timestamp receivingRequestDateTime;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@Column(name = "SENDING_RESPONSE_DATE_TIME")
	private Timestamp sendingResponseDateTime;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "STATUS_DESCRIPTION")
	private String statusDescription;

	@Column(name = "TRANSACTION_ID")
	private BigDecimal transactionId;

	@Column(name = "TRANSACTION_REFERENCE_NUMBER")
	private String transactionReferenceNumber;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "TRANSACTION_STATUS")
	private String transactionStatus;

	public long getTransactionLogId() {
		return this.transactionLogId;
	}

	public void setTransactionLogId(long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	public String getHttpStatus() {
		return this.httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpStatusDescription() {
		return this.httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Timestamp getReceivingRequestDateTime() {
		return this.receivingRequestDateTime;
	}

	public void setReceivingRequestDateTime(Timestamp receivingRequestDateTime) {
		this.receivingRequestDateTime = receivingRequestDateTime;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Timestamp getSendingResponseDateTime() {
		return this.sendingResponseDateTime;
	}

	public void setSendingResponseDateTime(Timestamp sendingResponseDateTime) {
		this.sendingResponseDateTime = sendingResponseDateTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return this.statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public BigDecimal getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionReferenceNumber() {
		return this.transactionReferenceNumber;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public TransactionLog() {
	}

	public TransactionLog(String payerId, String providerId, Timestamp receivingRequestDateTime, String requestId,
			String transactionType, String transactionStatus, BigDecimal transactionId) {
		super();
		this.payerId = payerId;
		this.providerId = providerId;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.requestId = requestId;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.transactionId = transactionId;
	}
}