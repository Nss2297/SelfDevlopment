package com.waseel.pbmnotificationservice.persist.businessrules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_SMS_AUDITLOG", schema = "PBM_BUSINESS_RULES")
public class EmailSmsAuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMAIL_SMS_AUDITLOG_ID", nullable = false, updatable = false)
	private Long emailSmsAuditLogId;

	@Column(name = "MOBILE_NUMBER", length = 15)
	private String mobileNumber;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "REQUEST_TYPE", nullable = false, length = 30)
	private String requestType;

	@Column(name = "STATUS", length = 60)
	private String status;

	@Column(name = "STATUS_DESCRIPTION", length = 1500)
	private String statusDescription;

	@Column(name = "TRANSACTION_STATUS", nullable = false, length = 10)
	private String transactionStatus;

	@Column(name = "RECEIVING_RESPONSE_DATE_TIME")
	private Timestamp receivingResponseDateTime;

	@Column(name = "SENDING_REQUEST_DATE_TIME", nullable = false)
	private Timestamp sendingRequestDateTime;

	@Column(name = "REQUEST_ID", nullable = false, length = 100)
	private String requestId;

	@Column(name = "EPRESCRIPTION_REFERENCE_NUMBER", nullable = false, length = 100)
	private String eprescriptionReferenceNumber;

	@Column(name = "UNIFONIC_MESSAGE_ID", length = 15)
	private String unifonicMessageId;

	@Column(name = "UNIFONIC_TIME_CREATED", length = 15)
	private String unifonicTimeCreated;

	@Column(name = "PATIENT_URL", length = 2000)
	private String patientUrl;

	public String getPatientUrl() {
		return patientUrl;
	}

	public void setPatientUrl(String patientUrl) {
		this.patientUrl = patientUrl;
	}

	public Long getEmailSmsAuditLogId() {
		return emailSmsAuditLogId;
	}

	public void setEmailSmsAuditLogId(Long emailSmsAuditLogId) {
		this.emailSmsAuditLogId = emailSmsAuditLogId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Timestamp getReceivingResponseDateTime() {
		return receivingResponseDateTime;
	}

	public void setReceivingResponseDateTime(Timestamp receivingResponseDateTime) {
		this.receivingResponseDateTime = receivingResponseDateTime;
	}

	public Timestamp getSendingRequestDateTime() {
		return sendingRequestDateTime;
	}

	public void setSendingRequestDateTime(Timestamp sendingRequestDateTime) {
		this.sendingRequestDateTime = sendingRequestDateTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getEprescriptionReferenceNumber() {
		return eprescriptionReferenceNumber;
	}

	public void setEprescriptionReferenceNumber(String eprescriptionReferenceNumber) {
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
	}

	public String getUnifonicMessageId() {
		return unifonicMessageId;
	}

	public void setUnifonicMessageId(String unifonicMessageId) {
		this.unifonicMessageId = unifonicMessageId;
	}

	public String getUnifonicTimeCreated() {
		return unifonicTimeCreated;
	}

	public void setUnifonicTimeCreated(String unifonicTimeCreated) {
		this.unifonicTimeCreated = unifonicTimeCreated;
	}

	public EmailSmsAuditLog() {
		super();
	}

	public EmailSmsAuditLog(Long emailSmsAuditLogId, String mobileNumber, String email, String requestType,
			String status, String statusDescription, String transactionStatus, Timestamp receivingResponseDateTime,
			Timestamp sendingRequestDateTime, String requestId, String eprescriptionReferenceNumber,
			String unifonicMessageId, String unifonicTimeCreated, String patientUrl) {
		super();
		this.emailSmsAuditLogId = emailSmsAuditLogId;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.requestType = requestType;
		this.status = status;
		this.statusDescription = statusDescription;
		this.transactionStatus = transactionStatus;
		this.receivingResponseDateTime = receivingResponseDateTime;
		this.sendingRequestDateTime = sendingRequestDateTime;
		this.requestId = requestId;
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
		this.unifonicMessageId = unifonicMessageId;
		this.unifonicTimeCreated = unifonicTimeCreated;
		this.patientUrl = patientUrl;
	}

}
