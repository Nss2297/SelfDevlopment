package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "TransactionLog", schema = "PRESCRIPTION_SERVICE")
public class TransactionLog implements Serializable {

	private static final long serialVersionUID = -1818254004699892876L;

	@Id
	@GeneratedValue(generator = "PsTransactionLogSeq")
	@SequenceGenerator(name = "PsTransactionLogSeq", sequenceName = "PS_TransactionLog_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "TransactionLogID", unique = true, nullable = false, precision = 0)
	private Long transactionLogId;

	@Column(name = "RequestID", length = 100, nullable = false)
	private String requestId;

	@Column(name = "TransactionID", nullable = false)
	private Double transactionID;

	@Column(name = "TransactionType", length = 20, nullable = false)
	private String transactionType;

	@Column(name = "PayerID", length = 20)
	private String payerId;

	@Column(name = "ProviderID", length = 20)
	private String providerId;

	@Column(name = "TransactionStatus", length = 40, nullable = false)
	private String transactionStatus;

	@Column(name = "EPrescriptionReferenceNumber", length = 100, nullable = false)
	private String ePrescriptionReferenceNumber;

	@Column(name = "Status", length = 60)
	private String status;

	@Column(name = "StatusDescription", length = 500, nullable = false)
	private String statusDescription;

	@Column(name = "ReceivingRequestDateTime", length = 6, nullable = false)
	private Timestamp receivingRequestDateTime;

	@Column(name = "SendingResponseDateTime", length = 6, nullable = false)
	private Timestamp sendingResponseDateTime;

	@Column(name = "UserID", length = 100)
	private String userID;

	@Column(name = "HttpStatus", length = 30)
	private String httpStatus;

	@Column(name = "HttpStatusDescription", length = 5000)
	private String httpStatusDescription;

	@Column(name = "SourceType", length = 10, nullable = false)
	private String sourceType;

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
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
		this.statusDescription = StringUtils.isNotBlank(statusDescription)
				? truncateStatusDescription(statusDescription)
				: statusDescription;
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Double getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Double transactionID) {
		this.transactionID = transactionID;
	}

	public TransactionLog() {
		super();
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public TransactionLog(String requestId, Double transactionID, String transactionType, String payerId,
			String providerId, String transactionStatus, String ePrescriptionReferenceNumber, String status,
			String statusDescription, Timestamp receivingRequestDateTime, Timestamp sendingResponseDateTime,
			String userID, String httpStatus, String httpStatusDescription) {
		super();
		this.requestId = requestId;
		this.transactionID = transactionID;
		this.transactionType = transactionType;
		this.payerId = payerId;
		this.providerId = providerId;
		this.transactionStatus = transactionStatus;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.userID = userID;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
	}

	public TransactionLog(Long transactionLogId, String requestId, Double transactionID, String transactionType,
			String payerId, String providerId, String transactionStatus, String ePrescriptionReferenceNumber,
			String status, String statusDescription, Timestamp receivingRequestDateTime,
			Timestamp sendingResponseDateTime, String userID, String httpStatus, String httpStatusDescription) {
		super();
		this.transactionLogId = transactionLogId;
		this.requestId = requestId;
		this.transactionID = transactionID;
		this.transactionType = transactionType;
		this.payerId = payerId;
		this.providerId = providerId;
		this.transactionStatus = transactionStatus;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.userID = userID;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
	}

	@Override
	public String toString() {
		return "TransactionLog [transactionLogId=" + transactionLogId + ", requestId=" + requestId + ", transactionID="
				+ transactionID + ", transactionType=" + transactionType + ", payerId=" + payerId + ", providerId="
				+ providerId + ", transactionStatus=" + transactionStatus + ", ePrescriptionReferenceNumber="
				+ ePrescriptionReferenceNumber + ", status=" + status + ", statusDescription=" + statusDescription
				+ ", receivingRequestDateTime=" + receivingRequestDateTime + ", sendingResponseDateTime="
				+ sendingResponseDateTime + ", userID=" + userID + ", httpStatus=" + httpStatus
				+ ", httpStatusDescription=" + httpStatusDescription + ", getTransactionLogId()="
				+ getTransactionLogId() + ", getRequestId()=" + getRequestId() + ", getTransactionType()="
				+ getTransactionType() + ", getPayerId()=" + getPayerId() + ", getProviderId()=" + getProviderId()
				+ ", getePrescriptionReferenceNumber()=" + getePrescriptionReferenceNumber() + ", getStatus()="
				+ getStatus() + ", getStatusDescription()=" + getStatusDescription()
				+ ", getReceivingRequestDateTime()=" + getReceivingRequestDateTime() + ", getSendingResponseDateTime()="
				+ getSendingResponseDateTime() + ", getUserID()=" + getUserID() + ", getHttpStatus()=" + getHttpStatus()
				+ ", getHttpStatusDescription()=" + getHttpStatusDescription() + ", getTransactionStatus()="
				+ getTransactionStatus() + ", getTransactionID()=" + getTransactionID() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public TransactionLog(String requestId, Double transactionID, String transactionType, String payerId,
			String providerId, String transactionStatus, String ePrescriptionReferenceNumber, String status,
			String statusDescription, Timestamp receivingRequestDateTime, Timestamp sendingResponseDateTime,
			String userID, String httpStatus, String httpStatusDescription, String sourceType) {
		super();
		this.requestId = requestId;
		this.transactionID = transactionID;
		this.transactionType = transactionType;
		this.payerId = payerId;
		this.providerId = providerId;
		this.transactionStatus = transactionStatus;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.userID = userID;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
		this.sourceType = sourceType;
	}

	private String truncateStatusDescription(String description) {
	    return (description.getBytes().length > 500)
	        ? description.substring(0, 497) + "..."
	        : description;
	}
}