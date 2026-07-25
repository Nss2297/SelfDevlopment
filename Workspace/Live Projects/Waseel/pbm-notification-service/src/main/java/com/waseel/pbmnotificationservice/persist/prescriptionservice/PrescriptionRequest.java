package com.waseel.pbmnotificationservice.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "`PrescriptionRequest`", schema = "PRESCRIPTION_SERVICE")

public class PrescriptionRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "`RequestID`", nullable = false, length = 100, precision = 0)
	private String requestId;

	@Column(name = "`PayerID`", length = 20, precision = 0)
	private String payerId;

	@Column(name = "`ProviderID`", length = 20, precision = 0)
	private String providerId;

	@Column(name = "`SendDateTime`", length = 6)
	private Timestamp sendDateTime;

	@Column(name = "`ReceivedDateTime`", length = 6)
	private Timestamp receivedDateTime;

	@Column(name = "`StatusCode`")
	private String statusCode;

	@Column(name = "`StatusDescription`", length = 3000)
	private String statusDescription;

	@Column(name = "`canCancel`", columnDefinition = "CHAR(1) default ('1')")
	private boolean canCancel = true;

	@Column(name = "`canFollowUp`", columnDefinition = "CHAR(1) default ('1')")
	private boolean canFollowUp = true;

	@Column(name = "`IsCancelled`", columnDefinition = "CHAR(1) default ('0')")
	private boolean isCancelled = false;

	@Column(name = "`EPrescriptionReferenceNumber`", length = 100)
	private String ePrescriptionReferenceNumber;

	@Column(name = "`CaseType`", length = 50)
	private String caseType;

	@Column(name = "`PatientShare`", nullable = false)
	private BigDecimal patientShare;

	@Column(name = "`PayerShare`", nullable = false)
	private BigDecimal payerShare;

	public PrescriptionRequest() {
		super();
	}
	
	public PrescriptionRequest(String requestId, String payerId, String providerId, Timestamp sendDateTime,
			Timestamp receivedDateTime, String statusCode, String statusDescription, boolean canCancel,
			boolean canFollowUp, boolean isCancelled, String ePrescriptionReferenceNumber, String caseType,
			BigDecimal patientShare, BigDecimal payerShare) {
		super();
		this.requestId = requestId;
		this.payerId = payerId;
		this.providerId = providerId;
		this.sendDateTime = sendDateTime;
		this.receivedDateTime = receivedDateTime;
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.canCancel = canCancel;
		this.canFollowUp = canFollowUp;
		this.isCancelled = isCancelled;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.caseType = caseType;
		this.patientShare = patientShare;
		this.payerShare = payerShare;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public Timestamp getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(Timestamp sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public Timestamp getReceivedDateTime() {
		return receivedDateTime;
	}

	public void setReceivedDateTime(Timestamp receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public boolean getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public boolean getCanFollowUp() {
		return canFollowUp;
	}

	public void setCanFollowUp(boolean canFollowUp) {
		this.canFollowUp = canFollowUp;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
	}
}