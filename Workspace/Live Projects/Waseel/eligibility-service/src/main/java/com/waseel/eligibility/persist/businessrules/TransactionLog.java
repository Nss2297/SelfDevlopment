package com.waseel.eligibility.persist.businessrules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pca46
 *
 */
@Entity
@Table(name = "TRANSACTION_LOG", schema = "PBM_BUSINESS_RULES")
public class TransactionLog implements Serializable {

	private static final long serialVersionUID = -3536981445601333997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACTION_LOG_ID", unique = true, nullable = false, precision = 0)
	private Long transactionLogId;

	@Column(name = "TRANSACTION_ID", nullable = false)
	private Double transactionID;

	@Column(name = "REQUEST_ID", length = 100)
	private String requestId;

	@Column(name = "TRANSACTION_REFERENCE_NUMBER")
	private String transactionReferenceNumber;

	@Column(name = "PAYER_ID", length = 20)
	private String payerId;

	@Column(name = "PROVIDER_ID", length = 20)
	private String providerId;

	@Column(name = "STATUS", length = 60)
	private String status;

	@Column(name = "STATUS_DESCRIPTION", length = 500)
	private String statusDescription;

	@Column(name = "RECEIVING_REQUEST_DATE_TIME", length = 6, nullable = false)
	private Timestamp receivingRequestDateTime;

	@Column(name = "SENDING_RESPONSE_DATE_TIME", length = 6, nullable = false)
	private Timestamp sendingResponseDateTime;

	@Column(name = "HTTP_STATUS", length = 30)
	private String httpStatus;

	@Column(name = "HTTP_STATUS_DESCRIPTION", length = 5000)
	private String httpStatusDescription;

	@Column(name = "TRANSACTION_TYPE", length = 100, nullable = false)
	private String transactionType;

	@Column(name = "TRANSACTION_STATUS", length = 40, nullable = false)
	private String transactionStatus;

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public Double getTransactionID() {
		return transactionID;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public Timestamp getReceivingRequestDateTime() {
		return receivingRequestDateTime;
	}

	public Timestamp getSendingResponseDateTime() {
		return sendingResponseDateTime;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public String getHttpStatusDescription() {
		return httpStatusDescription;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	public void setTransactionID(Double transactionID) {
		this.transactionID = transactionID;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public void setReceivingRequestDateTime(Timestamp receivingRequestDateTime) {
		this.receivingRequestDateTime = receivingRequestDateTime;
	}

	public void setSendingResponseDateTime(Timestamp sendingResponseDateTime) {
		this.sendingResponseDateTime = sendingResponseDateTime;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public String getTransactionType() {
		return transactionType;
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
		super();
	}

	public TransactionLog(Double transactionID, String payerId, String providerId, Timestamp receivingRequestDateTime,
			Timestamp sendingResponseDateTime, String transactionType, String requestId, String transactionStatus) {
		super();
		this.transactionID = transactionID;
		this.payerId = payerId;
		this.providerId = providerId;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.transactionType = transactionType;
		this.requestId = requestId;
		this.transactionStatus = transactionStatus;
	}

	public TransactionLog(Double transactionID, String requestId, String transactionReferenceNumber, String payerId,
			String providerId, String status, String statusDescription, Timestamp receivingRequestDateTime,
			Timestamp sendingResponseDateTime, String httpStatus, String httpStatusDescription,
			String transactionType) {
		super();
		this.transactionID = transactionID;
		this.requestId = requestId;
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.payerId = payerId;
		this.providerId = providerId;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
		this.transactionType = transactionType;
	}

	public TransactionLog(Long transactionLogId, Double transactionID, String requestId,
			String transactionReferenceNumber, String payerId, String providerId, String status,
			String statusDescription, Timestamp receivingRequestDateTime, Timestamp sendingResponseDateTime,
			String httpStatus, String httpStatusDescription, String transactionType, String transactionStatus) {
		super();
		this.transactionLogId = transactionLogId;
		this.transactionID = transactionID;
		this.requestId = requestId;
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.payerId = payerId;
		this.providerId = providerId;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
	}

}