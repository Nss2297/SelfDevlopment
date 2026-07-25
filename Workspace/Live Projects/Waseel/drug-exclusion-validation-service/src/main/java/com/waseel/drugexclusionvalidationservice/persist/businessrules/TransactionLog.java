package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_LOG", schema = "PBM_BUSINESS_RULES")
public class TransactionLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACTION_LOG_ID", nullable = false, updatable = false)
	private Long transactionLogId;

	@Column(name = "TRANSACTION_ID", nullable = false)
	private Double transactionId;

	@Column(name = "REQUEST_ID", length = 100)
	private String requestId;

	@Column(name = "TRANSACTION_TYPE", nullable = false, length = 100)
	private String transactionType;

	@Column(name = "TRANSACTION_STATUS", nullable = false, length = 100)
	private String transactionStatus;

	@Column(name = "PAYER_ID", length = 20)
	private String payerId;

	@Column(name = "TRANSACTION_REFERENCE_NUMBER", length = 20)
	private String transactionReferenceNumber;

	@Column(name = "PROVIDER_ID", length = 20)
	private String providerId;

	@Column(name = "STATUS", length = 60)
	private String status;

	@Column(name = "STATUS_DESCRIPTION", length = 500)
	private String statusDescription;

	@Column(name = "RECEIVING_REQUEST_DATE_TIME", nullable = false)
	private Timestamp receivingRequestDateTime;

	@Column(name = "SENDING_RESPONSE_DATE_TIME", nullable = false)
	private Timestamp sendingResponseDateTime;

	@Column(name = "HTTP_STATUS", length = 30)
	private String httpStatus;

	@Column(name = "HTTP_STATUS_DESCRIPTION", length = 5000)
	private String httpStatusDescription;

	@Column(name = "TRANSACTION_URL", length = 250)
	private String transactionURL;

	public String getTransactionURL() {
		return transactionURL;
	}

	public void setTransactionURL(String transactionURL) {
		this.transactionURL = transactionURL;
	}

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Timestamp getReceivingRequestDateTime() {
		return receivingRequestDateTime;
	}

	public void setReceivingRequestDateTime(Timestamp receivingRequestDateTime) {
		this.receivingRequestDateTime = receivingRequestDateTime;
	}

	public Timestamp getSendingResponseDateTime() {
		return sendingResponseDateTime;
	}

	public void setSendingResponseDateTime(Timestamp sendingResponseDateTime) {
		this.sendingResponseDateTime = sendingResponseDateTime;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpStatusDescription() {
		return httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public Double getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Double double1) {
		this.transactionId = double1;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public TransactionLog() {
	}

	public TransactionLog(Long transactionLogId, Double transactionId, String requestId, String transactionType,
			String transactionStatus, String payerId, String transactionReferenceNumber, String providerId,
			String status, String statusDescription, Timestamp receivingRequestDateTime,
			Timestamp sendingResponseDateTime, String httpStatus, String httpStatusDescription, String transactionURL) {
		this.transactionLogId = transactionLogId;
		this.transactionId = transactionId;
		this.requestId = requestId;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.payerId = payerId;
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.providerId = providerId;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
		this.transactionURL = transactionURL;
	}
}
